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

    private val rocketViewModel: RocketDetailViewModel by viewModels()
    @Inject lateinit var viewHoldersManager: ViewHoldersManager
    private lateinit var binding: FragmentRocketDetailBinding
    private var writeGranted = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rocket_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.detailsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
        }

        rocketViewModel.state.set("locale", requireContext().currentLocaleLang())
        rocketViewModel.loadRocket()

        checkWritePermission { writeGranted = it }

        return binding.root
    }

    private fun observeState() {
        lifecycleScope.launch {
            rocketViewModel.getRocketState()
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { state ->
                    when (state) {
                        is Loading -> {
                            binding.detailsView replaceBy binding.shimmerLayout.shimmer
                            binding.shimmerLayout.shimmer.startShimmer()
                            activity?.title = getString(R.string.loadingText)
                        }
                        is Error -> {
                            binding.shimmerLayout.shimmer.stopShimmer()
                            Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                            activity?.title = getString(R.string.error_title)
                        }
                        is Success<*> -> {
                            binding.shimmerLayout.shimmer.stopShimmer()
                            binding.shimmerLayout.shimmer replaceBy binding.detailsView
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
            if (writeGranted) setImageClickListener(downloadImageFromCarousel(requireContext(), rocket.images, "images_${rocket.name}.jpg"))
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
        binding.detailsList.adapter = detailsAdapter
    }
}