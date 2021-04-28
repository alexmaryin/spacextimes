package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.databinding.FragmentLaunchDetailBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.ItemTypes
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.CommonAdapters
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.LaunchDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LaunchDetailFragment : Fragment() {

    private val args: LaunchDetailFragmentArgs by navArgs()
    private val launchViewModel: LaunchDetailViewModel by viewModels()
    @Inject lateinit var viewHoldersManager: ViewHoldersManager
    private lateinit var binding: FragmentLaunchDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_launch_detail, container, false)
        binding.lifecycleOwner = this

        launchViewModel.state.set("launchId", args.launchId)
        launchViewModel.state.set("locale", requireContext().currentLocaleLang())
        launchViewModel.loadLaunch()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            launchViewModel.getLaunchState()
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

    private fun bindDetails(launch: Launch) {
        activity?.title = launch.name
        binding.launch = launch
        binding.imagesCarousel.apply {
            if (launch.images.isNotEmpty()) {
                visibility = View.VISIBLE
                setImageListener { position, imageView ->
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    CommonAdapters.loadImage(imageView, launch.images[position])
                }
                setImageClickListener(downloadImageFromCarousel(requireContext(), launch.images, "images_${launch.name}.jpg"))
                pageCount = launch.images.size
            } else {
                visibility = View.GONE
            }
        }

        // countdown timer if launch scheduled in next 24 hour
        val time = (launch.dateLocal.time - Calendar.getInstance().time.time)
        if (time.scheduledToNextDay()) {
            with (binding.countdownTimer) {
                visibility = View.VISIBLE
                isCountDown = true
                base = SystemClock.elapsedRealtime() + time
                start()
            }
        } else {
            binding.countdownTimer.visibility = View.GONE
        }

        val detailsAdapter = BaseListAdapter(AdapterClickListenerById { id, itemType ->
            when(itemType) {
                ItemTypes.CORE -> findNavController().navigate(LaunchDetailFragmentDirections.actionShowCoreDetails(id))
                ItemTypes.CAPSULE -> findNavController().navigate(LaunchDetailFragmentDirections.actionShowCapsuleDetails(id))
                ItemTypes.CREW -> findNavController().navigate(LaunchDetailFragmentDirections.actionShowCrewDetails(id))
                ItemTypes.LAUNCH_PAD -> findNavController().navigate(LaunchDetailFragmentDirections.actionShowLaunchPadDetails(id))
                ItemTypes.PAYLOAD -> findNavController().navigate(LaunchDetailFragmentDirections.actionShowPayloadDetails(id))
                ItemTypes.LINKS -> {
                    Toast.makeText(requireContext(), getString(R.string.open_link_announce), Toast.LENGTH_SHORT).show()
                    binding.detailsList.openLink(id)
                }
            }
        }, viewHoldersManager)
        detailsAdapter.submitList(launchViewModel.composeDetails(requireContext(), launch))
        binding.detailsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
            adapter = detailsAdapter
        }
    }
}