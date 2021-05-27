package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.databinding.FragmentMainBinding
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.*
import ru.alexmaryin.spacextimes_rx.utils.*
import java.util.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@AndroidEntryPoint
class MainFragment : Fragment() {

    private val spaceXViewModel: SpaceXViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    @Inject lateinit var settings: Settings
    @Inject lateinit var viewHoldersManager: ViewHoldersManager

    private var backPressedTime: Long = 0
    private val backPressHandler = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (spaceXViewModel.currentScreen is Launches) {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    activity?.finish()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.press_back_finish_message), Toast.LENGTH_SHORT).show()
                    backPressedTime = System.currentTimeMillis()
                }
            } else {
                spaceXViewModel.changeScreen(Launches)
                activity?.invalidateOptionsMenu()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, backPressHandler)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spaceXViewModel = spaceXViewModel
        spaceXViewModel.changeScreen(spaceXViewModel.currentScreen)
        collectState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.translateSwitch).isChecked = settings.translateToRu
        menu.findItem(R.id.filterAction).isVisible = spaceXViewModel.isFilterAvailable
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.launchesSelect -> spaceXViewModel.changeScreen(Launches)
            R.id.capsulesSelect -> spaceXViewModel.changeScreen(Capsules)
            R.id.coresSelect -> spaceXViewModel.changeScreen(Cores)
            R.id.crewSelect -> spaceXViewModel.changeScreen(Crew)
            R.id.dragonsSelect -> spaceXViewModel.changeScreen(Dragons)
            R.id.rocketsSelect -> spaceXViewModel.changeScreen(Rockets)
            R.id.launchPadsSelect -> spaceXViewModel.changeScreen(LaunchPads)
            R.id.landingPadsSelect -> spaceXViewModel.changeScreen(LandingPads)
            R.id.historySelect -> spaceXViewModel.changeScreen(HistoryEvents)
            R.id.filterAction -> binding.filterGroup.swapVisibility()
            R.id.syncAction -> spaceXViewModel.startSynchronization()
            R.id.translateSwitch -> {
                if (item.isChecked) {
                    item.isChecked = false
                    Toast.makeText(this.context, getString(R.string.aiTranslateOffText), Toast.LENGTH_SHORT).show()
                    processTranslate(false)
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.experimentalTitle))
                        .setMessage(getString(R.string.aiTranslateAlertText))
                        .setPositiveButton(getString(R.string.agreeText)) { _: DialogInterface, _: Int -> item.isChecked = true; processTranslate(true) }
                        .setNegativeButton(getString(R.string.cancelText)) { _: DialogInterface, _: Int -> item.isChecked = false }
                        .show()
                }
            }
        }
        activity?.invalidateOptionsMenu()
        if (item.itemId != R.id.filterAction) binding.filterGroup.visibility = View.GONE
        return super.onOptionsItemSelected(item)
    }

    private fun processTranslate(switch: Boolean) {
        settings.translateToRu = switch
        spaceXViewModel.armRefresh()
    }

    private fun collectState() {

        spaceXViewModel.getState().collectOnFragment(this) { state ->
            binding.filterGroup.visibility = View.GONE
            when (state) {
                Loading -> {
                    binding.recyclerView replaceBy binding.shimmerLayout.shimmer
                    binding.shimmerLayout.shimmer.startShimmer()
                    activity?.title = getString(R.string.loadingText)
                }
                is Success<*> -> {
                    activity?.title = getString(spaceXViewModel.currentScreen.titleRes)
                    binding.recyclerView.adapter = BaseListAdapter(
                        spaceXViewModel.currentScreen.setClickListener(findNavController()),
                        viewHoldersManager).apply { submitList(state.toListOf()!!) }
                    binding.shimmerLayout.shimmer replaceBy binding.recyclerView
                    binding.shimmerLayout.shimmer.stopShimmer()
                    populateFilterGroup()
                }
                is Error -> {
                    binding.shimmerLayout.shimmer.stopShimmer()
                    if (state.error == ErrorType.REMOTE_TRANSLATOR_ERROR)
                        processTranslate(false)
                    Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                    activity?.title = getString(R.string.error_title)
                }
            }
        }

        spaceXViewModel.getScrollTrigger().collectOnFragment(this) { state ->
            when (state) {
                false -> Toast.makeText(requireContext(), getString(R.string.upcoming_launches_deselected_string), Toast.LENGTH_SHORT).show()
                true -> {
                    val launches = (binding.recyclerView.adapter as BaseListAdapter).currentList.map { it as Launch }
                    spaceXViewModel.getNextLaunchPosition(launches)?.let { position ->
                        binding.recyclerView.addItemShaker(position)
                        val timeTo = (launches[position].dateLocal.time - Calendar.getInstance().time.time).prettifyMillisecondsPeriod(requireContext())
                        Snackbar.make(binding.recyclerView, getString(R.string.next_flight_announce, launches[position].name, timeTo), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
            with(binding.filterGroup) {
                postDelayed({ visibility = View.GONE }, 1000)
            }
        }
    }

    private fun populateFilterGroup() {
        spaceXViewModel.getFilterIfAvailable?.let { filter ->
            binding.filterGroup.removeAllViews()
            filter.filters.forEach { chipFilter ->
                binding.filterGroup.addView(Chip(requireContext()).apply {
                    text = getString(chipFilter.resId)
                    isCheckable = chipFilter.isCheckable
                    isChecked = chipFilter.checked
                    setOnClickListener {
                        if (isCheckable) {
                           toggle()
                           chipFilter.toggle()
                        }
                        chipFilter.onClick(spaceXViewModel)
                    }
                })
            }
        }
    }
}