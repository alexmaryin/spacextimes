package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.databinding.DragonDetailFragmentBinding
import ru.alexmaryin.spacextimes_rx.ui.view.bindAdapters.ImageAdapter.loadImage
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.DragonDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.*

@AndroidEntryPoint
class DragonDetailFragment : Fragment() {

    private val args: DragonDetailFragmentArgs by navArgs()
    private val dragonViewModel: DragonDetailViewModel by activityViewModels()
    private lateinit var binding: DragonDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.loadingText)
        binding = DataBindingUtil.inflate(inflater, R.layout.dragon_detail_fragment, container, false)
        binding.dragonViewModel = dragonViewModel
        binding.lifecycleOwner = this
        binding.wikiFrame.wikiPage.attachProgressAndRootView(binding.wikiFrame.wikiProgress, binding.detailsView)
        binding.imagesCarousel.setImageListener { position, imageView -> loadImage(imageView, dragonViewModel.dragonDetails.value?.images?.get(position)) }

        dragonViewModel.state.set("dragonId", args.dragonId)
        dragonViewModel.state.set("locale", requireContext().getCurrentLocale())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dragonViewModel.dragon.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> {
                    binding.wikiFrame.progress.visibility = View.VISIBLE
                    binding.detailsView.visibility = View.GONE
                    activity?.title = getString(R.string.loadingText)
                }
                is Error -> {
                    binding.wikiFrame.progress.visibility = View.GONE
                    Toast.makeText(this.context, state.msg, Toast.LENGTH_SHORT).show()
                }
                is Success<*> -> {
                    binding.wikiFrame.progress.visibility = View.GONE
                    binding.detailsView.visibility = View.VISIBLE
                    activity?.title = dragonViewModel.getTitle()
                }
            }
        }

        dragonViewModel.dragonDetails.observe(viewLifecycleOwner) { dragon ->
            binding.wikiButton.setOnClickListener { binding.wikiFrame.wikiPage.loadUrl(dragon.wikiLocale ?: dragon.wikipedia ?: "") }
            binding.imagesCarousel.pageCount = dragon.images.size

            binding.enginesList.adapter = SimpleAdapter(
                requireContext(),
                dragonViewModel.thrustersMap(requireContext()),
                android.R.layout.simple_list_item_2,
                dragonViewModel.thrustersLines,
                arrayOf(android.R.id.text1, android.R.id.text2).toIntArray()
            )
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}