package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.FragmentMainBinding
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters.*
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.Screen
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.SpaceXViewModel
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val spaceXViewModel: SpaceXViewModel by activityViewModels()
    private val capsulesAdapter = CapsuleAdapter(AdapterClickListenerById {})
    private val coreAdapter = CoreAdapter(AdapterClickListenerById { id ->
        findNavController().navigate(MainFragmentDirections.actionShowCoreDetails(id)) })
    private val dragonAdapter = DragonsAdapter(AdapterClickListenerById { id ->
        findNavController().navigate(MainFragmentDirections.actionShowDragonDetails(id)) })
    private val crewAdapter = CrewAdapter(AdapterClickListenerById { id ->
        findNavController().navigate(MainFragmentDirections.actionShowCrewMember(id)) })
    private val rocketAdapter = RocketAdapter(AdapterClickListenerById {})
    private val launchPadAdapter = LaunchPadAdapter(AdapterClickListenerById {})
    private val landingPadAdapter = LandingPadAdapter(AdapterClickListenerById {})
    private val launchesAdapter = LaunchesAdapter(AdapterClickListenerById {})

    private lateinit var binding: FragmentMainBinding
    @Inject lateinit var settings: Settings

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
        return super.onOptionsItemSelected(item)
    }

    private fun collectState() = lifecycleScope.launchWhenResumed {
            spaceXViewModel.getState().collect { state ->
                when (state) {
                    Loading -> {
                        binding.recyclerView replaceBy binding.progressBar
                        activity?.title = getString(R.string.loadingText)
                    }
                    is Success<*> -> {
                        when (spaceXViewModel.currentScreen) {
                            Screen.Capsules -> { renderItems(state.toListOf()!!, capsulesAdapter, R.string.capsulesTitle) }
                            Screen.Cores -> { renderItems(state.toListOf()!!, coreAdapter, R.string.coresTitle) }
                            Screen.Crew -> { renderItems(state.toListOf()!!, crewAdapter, R.string.crewTitle) }
                            Screen.Dragons -> { renderItems(state.toListOf()!!, dragonAdapter, R.string.dragonsTitle) }
                            Screen.Rockets -> { renderItems(state.toListOf()!!, rocketAdapter, R.string.rocketsTitle) }
                            Screen.Launches -> { renderItems(state.toListOf()!!, launchesAdapter, R.string.launchesTitle) }
                            Screen.LaunchPads -> { renderItems(state.toListOf()!!, launchPadAdapter, R.string.launchPadsTitle) }
                            Screen.LandingPads -> { renderItems(state.toListOf()!!, landingPadAdapter, R.string.landingPadsTitle) }
                        }
                        binding.progressBar replaceBy binding.recyclerView
                    }
                    is Error -> {
                        binding.progressBar.visibility = View.GONE
                        if (state.error == ErrorType.REMOTE_TRANSLATOR_ERROR)
                            processTranslate(false)
                        Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    private fun processTranslate(switch: Boolean) {
        settings.translateToRu = switch
        spaceXViewModel.armRefresh()
    }

    private fun <T : HasStringId> renderItems(items: List<T>, currentAdapter: BaseListAdapter<T>, titleResource: Int) {
        activity?.title = getString(titleResource)
        currentAdapter.submitList(items)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
            adapter = currentAdapter
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}