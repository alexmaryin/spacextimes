package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.FragmentMainBinding
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.Screen
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.SpaceXViewModel
import ru.alexmaryin.spacextimes_rx.utils.*
import java.util.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@AndroidEntryPoint
class MainFragment : Fragment() {

    private val coreClickListener = AdapterClickListenerById { id, _ ->
        findNavController().navigate(MainFragmentDirections.actionShowCoreDetails(id))
    }
    private val dragonClickListener = AdapterClickListenerById { id, _ ->
        findNavController().navigate(MainFragmentDirections.actionShowDragonDetails(id))
    }
    private val crewClickListener = AdapterClickListenerById { id, _ ->
        findNavController().navigate(MainFragmentDirections.actionShowCrewMember(id))
    }
    private val rocketClickListener = AdapterClickListenerById { id, _ ->
        findNavController().navigate(MainFragmentDirections.actionShowRocketDetails(id))
    }
    private val launchPadClickListener = AdapterClickListenerById { id, _ ->
        findNavController().navigate(MainFragmentDirections.actionShowLaunchPadDetails(id))
    }
    private val landingPadClickListener = AdapterClickListenerById { id, _ ->
        findNavController().navigate(MainFragmentDirections.actionShowLandingPadDetails(id))
    }
    private val capsuleClickListener = AdapterClickListenerById { id, _ ->
        findNavController().navigate(MainFragmentDirections.actionShowCapsuleDetails(id))
    }
    private val launchClickListener = AdapterClickListenerById { id, _ ->
        findNavController().navigate(MainFragmentDirections.actionShowLaunchDetails(id))
    }

    private val spaceXViewModel: SpaceXViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    @Inject
    lateinit var settings: Settings
    @Inject
    lateinit var viewHoldersManager: ViewHoldersManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
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
        menu.findItem(R.id.filterAction).isVisible = spaceXViewModel.currentScreen == Screen.Launches
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.launchesSelect -> spaceXViewModel.changeScreen(Screen.Launches)
            R.id.capsulesSelect -> spaceXViewModel.changeScreen(Screen.Capsules)
            R.id.coresSelect -> spaceXViewModel.changeScreen(Screen.Cores)
            R.id.crewSelect -> spaceXViewModel.changeScreen(Screen.Crew)
            R.id.dragonsSelect -> spaceXViewModel.changeScreen(Screen.Dragons)
            R.id.rocketsSelect -> spaceXViewModel.changeScreen(Screen.Rockets)
            R.id.launchPadsSelect -> spaceXViewModel.changeScreen(Screen.LaunchPads)
            R.id.landingPadsSelect -> spaceXViewModel.changeScreen(Screen.LandingPads)
            R.id.historySelect -> spaceXViewModel.changeScreen(Screen.HistoryEvents)
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
            R.id.filterAction -> processFilters()
        }
        activity?.invalidateOptionsMenu()
        if (item.itemId != R.id.filterAction) binding.filterGroup.visibility = View.GONE
        return super.onOptionsItemSelected(item)
    }

    private fun processFilters() {
        binding.filterGroup.swapVisibility()
    }

    private fun collectState() {
        lifecycleScope.launch {
            spaceXViewModel.getState()
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { state ->
                    when (state) {
                        Loading -> {
                            binding.recyclerView replaceBy binding.progressBar
                            activity?.title = getString(R.string.loadingText)
                        }
                        is Success<*> -> {
                            when (spaceXViewModel.currentScreen) {
                                Screen.Capsules -> {
                                    renderItems(state.toListOf()!!, R.string.capsulesTitle, capsuleClickListener)
                                }
                                Screen.Cores -> {
                                    renderItems(state.toListOf()!!, R.string.coresTitle, coreClickListener)
                                }
                                Screen.Crew -> {
                                    renderItems(state.toListOf()!!, R.string.crewTitle, crewClickListener)
                                }
                                Screen.Dragons -> {
                                    renderItems(state.toListOf()!!, R.string.dragonsTitle, dragonClickListener)
                                }
                                Screen.Rockets -> {
                                    renderItems(state.toListOf()!!, R.string.rocketsTitle, rocketClickListener)
                                }
                                Screen.Launches -> {
                                    renderItems(state.toListOf()!!, R.string.launchesTitle, launchClickListener)
                                }
                                Screen.LaunchPads -> {
                                    renderItems(state.toListOf()!!, R.string.launchPadsTitle, launchPadClickListener)
                                }
                                Screen.LandingPads -> {
                                    renderItems(state.toListOf()!!, R.string.landingPadsTitle, landingPadClickListener)
                                }
                                Screen.HistoryEvents -> {
                                    renderItems(state.toListOf()!!, R.string.historyEventsTitle)
                                }
                            }
                            binding.progressBar replaceBy binding.recyclerView
                        }
                        is Error -> {
                            binding.progressBar.visibility = View.GONE
                            if (state.error == ErrorType.REMOTE_TRANSLATOR_ERROR)
                                processTranslate(false)
                            Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                            activity?.title = getString(R.string.error_title)
                        }
                    }
                }
        }

        lifecycleScope.launch {
            spaceXViewModel.scrollTrigger
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { (position, launch) ->

                    val shaker = object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                            super.onScrollStateChanged(recyclerView, newState)
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                recyclerView.layoutManager?.findViewByPosition(position)?.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.shake))
                                recyclerView.removeOnScrollListener(this)
                            }
                        }
                    }

                    val scroller = object : LinearSmoothScroller(requireContext()) {
                        override fun getVerticalSnapPreference() = SNAP_TO_START
                    }
                    scroller.targetPosition = position

                    with(binding.recyclerView) {
                        // add shake animation after scrolling
                        addOnScrollListener(shaker)
                        // scroll to next launch
                        layoutManager?.startSmoothScroll(scroller)
                        // show snack with name of the next launch and remaining time
                        val timeTo = (launch.dateLocal.time - Calendar.getInstance().time.time).prettifyMillisecondsPeriod(requireContext())
                        val snack = Snackbar.make(this, getString(R.string.next_flight_announce, launch.name, timeTo), Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                }
        }
    }

    private fun processTranslate(switch: Boolean) {
        settings.translateToRu = switch
        spaceXViewModel.armRefresh()
    }

    private fun <T : HasStringId> renderItems(
        items: List<T>,
        titleResource: Int,
        clickListener: AdapterClickListenerById = AdapterClickListenerById { _, _ -> }
    ) {
        activity?.title = getString(titleResource)
        val currentAdapter = BaseListAdapter(clickListener, viewHoldersManager).apply { submitList(items) }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
            adapter = currentAdapter
        }
    }
}