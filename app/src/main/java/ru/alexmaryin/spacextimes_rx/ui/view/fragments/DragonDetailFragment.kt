package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import ru.alexmaryin.spacextimes_rx.databinding.DragonDetailFragmentBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.ImageAdapter.loadImage
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.DragonDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.*

@AndroidEntryPoint
class DragonDetailFragment : Fragment() {

    private val args: DragonDetailFragmentArgs by navArgs()
    private val dragonViewModel: DragonDetailViewModel by viewModels()
    private lateinit var binding: DragonDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.loadingText)
        binding = DataBindingUtil.inflate(inflater, R.layout.dragon_detail_fragment, container, false)
        binding.lifecycleOwner = this
        binding.wikiFrame.wikiPage.attachProgressAndRootView(binding.wikiFrame.wikiProgress, binding.detailsView)

        dragonViewModel.state.set("dragonId", args.dragonId)
        dragonViewModel.state.set("locale", requireContext().currentLocaleLang())
        dragonViewModel.loadDragon()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            dragonViewModel.getDragonState()
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { state ->
                when (state) {
                    is Loading -> {
                        binding.detailsView replaceBy binding.wikiFrame.progress
                        activity?.title = getString(R.string.loadingText)
                    }
                    is Error -> {
                        binding.wikiFrame.progress.visibility = View.GONE
                        Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                    }
                    is Success<*> -> {
                        binding.wikiFrame.progress replaceBy binding.detailsView
                        bindDetails(state.toDetails())
                    }
                }
            }
        }

    }

    private fun bindDetails(dragon: Dragon) {
        activity?.title = dragon.name
        binding.dragon = dragon
        binding.wikiButton.setOnClickListener { binding.wikiFrame.wikiPage.loadUrl(dragon.wikiLocale ?: dragon.wikipedia ?: "") }
        binding.imagesCarousel.apply {
            setImageListener { position, imageView -> loadImage(imageView, dragon.images[position]) }
            setOnLongClickListener(saveByLongClickListener(requireContext(), "${dragon.name}.jpg"))  // TODO("Don't work with CarouselView!")
            pageCount = dragon.images.size
        }
        binding.enginesList.adapter = SimpleAdapter(
            requireContext(),
            dragonViewModel.thrustersMap(requireContext(), dragon),
            android.R.layout.simple_list_item_2,
            dragonViewModel.thrustersLines,
            arrayOf(android.R.id.text1, android.R.id.text2).toIntArray()
        )
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}