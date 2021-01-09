package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.Assisted
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.databinding.FragmentMainBinding
import ru.alexmaryin.spacextimes_rx.di.module.Settings
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.spacex.CapsuleAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.spacex.CrewAdapter
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.Screen
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.SpaceXViewModel
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var screen: Screen = Screen.Capsules
    private val spaceXViewModel: SpaceXViewModel by viewModels()
    private val capsulesAdapter = CapsuleAdapter()
    private val crewAdapter = CrewAdapter()

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.spaceXViewModel = spaceXViewModel
        changeScreen(Screen.Crew, getString(R.string.crewTitle))
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
            R.id.capsulesSelect -> changeScreen(Screen.Capsules, item.title.toString())
            R.id.coresSelect -> changeScreen(Screen.Cores, item.title.toString())
            R.id.crewSelect -> changeScreen(Screen.Crew, item.title.toString())
            R.id.dragonsSelect -> changeScreen(Screen.Dragons, item.title.toString())
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

    private fun changeScreen(screen: Screen, itemTitle: String) {
        this.screen = screen
        activity?.title = itemTitle
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
        adapter.apply {
            addData(items)
            notifyDataSetChanged()
        }
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
        when (screen) {
            Screen.Capsules -> spaceXViewModel.capsules.observe(viewLifecycleOwner, { result -> itemObserver(result, capsulesAdapter) })
            Screen.Cores -> Unit
            Screen.Crew -> spaceXViewModel.crew.observe(viewLifecycleOwner, { result -> itemObserver(result, crewAdapter) })
            Screen.Dragons -> Unit
        }
    }

    /*
    * Setup recycler view adapter for actual screen
    * */

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainFragment.context)
            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
            adapter = when (screen) {
                Screen.Capsules -> capsulesAdapter
                Screen.Crew -> crewAdapter
                Screen.Cores -> capsulesAdapter
                Screen.Dragons -> capsulesAdapter
            }
        }
    }
}