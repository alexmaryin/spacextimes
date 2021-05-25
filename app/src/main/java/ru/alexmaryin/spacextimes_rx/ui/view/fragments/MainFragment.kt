package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.FragmentMainBinding
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
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
        menu.findItem(R.id.filterAction).isVisible = spaceXViewModel.currentScreen == Launches
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

//        spaceXViewModel.observeFilterChange().collectOnFragment(this) { (shown, total) ->
//            binding.filterGroup.visibility = View.GONE
//            Toast.makeText(requireContext(), getString(R.string.filtered_launches_toast, shown, total), Toast.LENGTH_SHORT).show()
//        }

        spaceXViewModel.getState().collectOnFragment(this) { state ->
            when (state) {
                Loading -> {
                    binding.recyclerView replaceBy binding.shimmerLayout.shimmer
                    binding.shimmerLayout.shimmer.startShimmer()
                    activity?.title = getString(R.string.loadingText)
                }
                is Success<*> -> {
                    renderItems(
                        state.toListOf()!!,
                        spaceXViewModel.currentScreen.titleRes,
                        spaceXViewModel.currentScreen.setClickListener(findNavController())
                    )
                    binding.shimmerLayout.shimmer replaceBy binding.recyclerView
                    binding.shimmerLayout.shimmer.stopShimmer()
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
            with (binding.filterGroup) {
                postDelayed({ visibility = View.GONE }, 1000)
            }
        }
    }

    private fun <T : HasStringId> renderItems(
        items: List<T>,
        titleResource: Int,
        clickListener: AdapterClickListenerById
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