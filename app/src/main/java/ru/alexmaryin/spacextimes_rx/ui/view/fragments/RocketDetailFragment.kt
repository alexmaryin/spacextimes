package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Rocket
import ru.alexmaryin.spacextimes_rx.databinding.FragmentRocketDetailBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.ItemTypes
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.CommonAdapters
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.RocketDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject

@AndroidEntryPoint
class RocketDetailFragment : Fragment() {

    private val args: RocketDetailFragmentArgs by navArgs()
    private val rocketViewModel: RocketDetailViewModel by viewModels()
    @Inject lateinit var viewHoldersManager: ViewHoldersManager
    private lateinit var binding: FragmentRocketDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rocket_detail, container, false)
        binding.lifecycleOwner = this

        rocketViewModel.state.set("rocketId", args.rocketId)
        rocketViewModel.state.set("locale", requireContext().currentLocaleLang())
        rocketViewModel.loadRocket()

        return binding.root
    }

    private fun observeState() {
        lifecycleScope.launch {
            rocketViewModel.getRocketState()
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { state ->
                    when (state) {
                        is Loading -> {
                            binding.detailsView replaceBy binding.progress
                            activity?.title = getString(R.string.loadingText)
                        }
                        is Error -> {
                            binding.progress.visibility = View.GONE
                            Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                            activity?.title = getString(R.string.error_title)
                        }
                        is Success<*> -> {
                            binding.progress replaceBy binding.detailsView
                            bindDetails(state.toDetails())
                        }
                    }
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun bindDetails(rocket: Rocket) {
        activity?.title = rocket.name
        binding.rocket = rocket
        binding.imagesCarousel.apply {
            setImageListener { position, imageView -> CommonAdapters.loadImage(imageView, rocket.images[position]) }
            setImageClickListener(downloadImageFromCarousel(requireContext(), rocket.images, "images_${rocket.name}.jpg"))
            pageCount = rocket.images.size
        }

        val detailsAdapter = BaseListAdapter(AdapterClickListenerById { id, itemType ->
            when (itemType) {
                ItemTypes.LINKS -> {
                    Toast.makeText(requireContext(), getString(R.string.open_link_announce), Toast.LENGTH_SHORT).show()
                    binding.detailsList.openLink(id)
                }
            }
        }, viewHoldersManager)
        detailsAdapter.submitList(rocketViewModel.composeDetails(requireContext(), rocket))
        binding.detailsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
            adapter = detailsAdapter
        }
    }
}