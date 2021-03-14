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
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.databinding.FragmentMainBinding
import ru.alexmaryin.spacextimes_rx.di.module.Settings
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters.CapsuleAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters.CoreAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters.CrewAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters.DragonsAdapter
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.Screen
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.SpaceXViewModel
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment() {

    private val spaceXViewModel: SpaceXViewModel by activityViewModels()
    private val capsulesAdapter = CapsuleAdapter(AdapterClickListenerById {})
    private val coreAdapter = CoreAdapter(AdapterClickListenerById {})
    private val dragonAdapter = DragonsAdapter(AdapterClickListenerById { id ->
        findNavController().navigate(MainFragmentDirections.actionShowDragonDetails(id)) })
    private val crewAdapter = CrewAdapter(AdapterClickListenerById { id ->
        findNavController().navigate(MainFragmentDirections.actionShowCrewMember(id)) })

    private lateinit var binding: FragmentMainBinding
    @Inject lateinit var settings: Settings

    /*
        Setup main UI of main fragment
    */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spaceXViewModel = spaceXViewModel
        changeScreen(spaceXViewModel.screen)
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
            R.id.capsulesSelect -> changeScreen(Screen.Capsules)
            R.id.coresSelect -> changeScreen(Screen.Cores)
            R.id.crewSelect -> changeScreen(Screen.Crew)
            R.id.dragonsSelect -> changeScreen(Screen.Dragons)
            R.id.translateSwitch -> {
                if (item.isChecked) {
                    item.isChecked = false
                    Toast.makeText(this.context, getString(R.string.aiTranslateOffText), Toast.LENGTH_SHORT).show()
                    processTranslate(false)
                } else {
                    this.context?.let {
                        AlertDialog.Builder(it)
                            .setTitle(getString(R.string.experimentalTitle))
                            .setMessage(getString(R.string.aiTranslateAlertText))
                            .setPositiveButton(getString(R.string.agreeText)) { _: DialogInterface, _: Int -> item.isChecked = true; processTranslate(true) }
                            .setNegativeButton(getString(R.string.cancelText)) { _: DialogInterface, _: Int -> item.isChecked = false }
                            .show()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeScreen(screen: Screen) {
        spaceXViewModel.screen = screen
        activity?.title = getString(when(screen) {
            Screen.Capsules -> R.string.capsulesTitle
            Screen.Cores -> R.string.coresTitle
            Screen.Crew -> R.string.crewTitle
            Screen.Dragons -> R.string.dragonsTitle
        })
        setupObserver()
        setupUI()
    }

    /*
        Functions for main view model managing
    */


    private fun processTranslate(switch: Boolean) {
        settings.translateToRu = switch
        spaceXViewModel.armRefresh()
        setupObserver()
    }

    private fun <T> renderItems(items: List<T>, adapter: BaseAdapter<T>) {
        adapter.addData(items)
    }

    private inline fun <reified T> itemObserver(state: Result, adapter: BaseAdapter<T>) =
        when (state) {
            is Success<*> -> {
                binding.progressBar.visibility = View.GONE
                (state.data as List<*>).map { it as T }.apply { renderItems(this, adapter) }
                binding.recyclerView.visibility = View.VISIBLE
            }
            is Error -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this.context, state.msg, Toast.LENGTH_SHORT).show()
            }
            is Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        }

    /*
    * Setup observer for actual screen
    * */

    private fun setupObserver() {
        when (spaceXViewModel.screen) {
            Screen.Capsules -> spaceXViewModel.capsules.observe(viewLifecycleOwner, { result -> itemObserver(result, capsulesAdapter) })
            Screen.Cores -> spaceXViewModel.cores.observe(viewLifecycleOwner, { result -> itemObserver(result, coreAdapter) })
            Screen.Crew -> spaceXViewModel.crew.observe(viewLifecycleOwner, { result -> itemObserver(result, crewAdapter) })
            Screen.Dragons -> spaceXViewModel.dragons.observe(viewLifecycleOwner, { result -> itemObserver(result, dragonAdapter) })
        }
    }

    /*
    * Setup recycler view adapter for actual screen
    * */

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainFragment.context)
            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
            adapter = when (spaceXViewModel.screen) {
                Screen.Capsules -> capsulesAdapter
                Screen.Crew -> crewAdapter
                Screen.Cores -> coreAdapter
                Screen.Dragons -> dragonAdapter
            }
        }
    }
}