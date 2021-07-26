package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.FragmentMainBinding
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.*
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@AndroidEntryPoint
class MainFragment : Fragment() {

    private val spaceXViewModel: SpaceXViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding

    @Inject lateinit var viewHoldersManager: ViewHoldersManager

    @Inject lateinit var settings: Settings
    private var forceStartScroll = false
    private val referenceList = mutableListOf<HasStringId>()

    private var backPressedTime: Long = 0
    private val backPressHandler = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (spaceXViewModel.currentScreen is Launches) {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    activity?.finishAndRemoveTask()
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

    private fun refreshSettings() = runBlocking {
        settings.saved.collect {
            requireActivity().setTheme(if (it.widePadding) R.style.Theme_Spacextimes_rx_WidePad else R.style.Theme_Spacextimes_rx_NormalPad)
            forceStartScroll = it.startNextLaunch
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressHandler)
        findNavController().currentBackStackEntry?.let {
            it.savedStateHandle.getLiveData<Boolean>(Settings.IS_PREFERENCES_CHANGED).observe(viewLifecycleOwner) { isChanged ->
                if (isChanged) {
//                    refreshSettings()
                    spaceXViewModel.armRefresh()
                    it.savedStateHandle.remove<Boolean>(Settings.IS_PREFERENCES_CHANGED)
                }
            }
        }

        refreshSettings()

        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
        }
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
        menu.findItem(R.id.filterAction).isVisible = spaceXViewModel.isFilterAvailable
        menu.findItem(R.id.searchAction).apply {
            if (spaceXViewModel.isSearchable) {
                isVisible = true
                val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
                with(actionView as SearchView) {
                    setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                    setOnQueryTextListener(HotSearchListener(this@MainFragment::hideKeyboard) { searchString -> renderList(searchString) })
                    setOnActionExpandListener(MenuItemCollapseListener { activity?.invalidateOptionsMenu() })
                }
            } else isVisible = false
        }
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

            R.id.syncAction -> lifecycleScope.launch {
                settings.armSynchronize = true
                spaceXViewModel.armRefresh()
            }

            R.id.preferences -> findNavController().navigate(MainFragmentDirections.actionShowPreferences())
        }
        activity?.invalidateOptionsMenu()
        if (item.itemId != R.id.filterAction) binding.filterGroup.visibility = View.GONE
        return super.onOptionsItemSelected(item)
    }

    private fun collectState() {

        spaceXViewModel.getState().collectOnFragment(this) { state ->
            binding.filterGroup.visibility = View.GONE
            binding.emptyListMessage.emptyListLayout.visibility = View.GONE
            when (state) {
                Loading -> {
                    binding.recyclerView replaceBy binding.shimmerLayout.shimmer
                    binding.shimmerLayout.shimmer.startShimmer()
                    activity?.title = getString(R.string.loadingText)
                }
                is Success<*> -> {
                    referenceList.clear()
                    referenceList.addAll(state.toListOf()!!)
                    binding.shimmerLayout.shimmer replaceBy binding.recyclerView
                    binding.shimmerLayout.shimmer.stopShimmer()
                    renderList()
                    populateFilterGroup()
                }
                is Error -> {
                    binding.shimmerLayout.shimmer.stopShimmer()
                    Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                    activity?.title = getString(R.string.error_title)
                    Log.d("ERROR_FETCH", state.msg)
//                    lifecycleScope.launch {
//                        settings.translateToRu(false)
//                        spaceXViewModel.armRefresh()
//                    }
                }
            }
        }

        spaceXViewModel.getScrollTrigger().collectOnFragment(this) { state ->
            when (state) {
                false -> Toast.makeText(requireContext(), getString(R.string.upcoming_launches_deselected_string), Toast.LENGTH_SHORT).show()
                true -> {
                    spaceXViewModel.getScrollPosition(requireContext(), referenceList)?.let { (position, snackText) ->
                        binding.recyclerView.addItemShaker(position)
                        if (snackText.isNotBlank()) Snackbar.make(binding.recyclerView, snackText, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
            with(binding.filterGroup) {
                postDelayed({ visibility = View.GONE }, 1000)
            }
        }
    }

    private fun renderList(searchString: String = "") {
        activity?.title = getString(spaceXViewModel.currentScreen.titleRes)
        binding.recyclerView.adapter = BaseListAdapter(
            spaceXViewModel.currentScreen.setClickListener(findNavController()),
            viewHoldersManager
        ).apply {
            val renderList = if (spaceXViewModel.isFilterAvailable) spaceXViewModel.filterOrSearch(referenceList, searchString) else referenceList
            if (renderList.isNotEmpty()) {
                binding.emptyListMessage.emptyListLayout replaceBy binding.recyclerView
                submitList(renderList)
                if (forceStartScroll)  spaceXViewModel.getScrollPosition(requireContext(), referenceList)?.let { (position, _) ->
                    binding.recyclerView.scrollToPosition(position)
                }
            } else {
                binding.recyclerView replaceBy binding.emptyListMessage.emptyListLayout
            }
        }
    }

    private fun populateFilterGroup() {
        spaceXViewModel.getFilterIfAvailable?.let { filter ->
            filter.searchString = ""
            binding.filterGroup.removeAllViews()
            filter.filters.forEach { chipFilter ->
                binding.filterGroup.addView(Chip(requireContext()).apply {
                    text = getString(chipFilter.resId)
                    isCheckable = chipFilter.isCheckable
                    isChecked = chipFilter.checked
                    setOnClickListener {
                        if (isCheckable) {
                            chipFilter.toggle()
                            renderList()
                            with(binding.filterGroup) {
                                postDelayed({ visibility = View.GONE }, 1000)
                            }
                        }
                        chipFilter.onClick(spaceXViewModel)
                    }
                })
            }
        }
    }
}
