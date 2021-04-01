package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.databinding.FragmentCoreDetailBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.asBody
import ru.alexmaryin.spacextimes_rx.ui.adapters.asHeader
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters.LaunchesAdapter
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CoreDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.*

@AndroidEntryPoint
class CoreDetailFragment : Fragment() {

    private val args: CoreDetailFragmentArgs by navArgs()
    private val coreViewModel: CoreDetailViewModel by viewModels()
    private lateinit var binding: FragmentCoreDetailBinding
    private val missionsAdapter = LaunchesAdapter(AdapterClickListenerById {})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_core_detail, container, false)
        binding.lifecycleOwner = this
        coreViewModel.state.set("coreId", args.coreId)

        lifecycleScope.launch {
            coreViewModel.getState()
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { state ->
                when (state) {
                    is Loading -> {
                        binding.detailsView replaceBy binding.progress
                        activity?.title = getString(R.string.loadingText)
                    }
                    is Error -> {
                        binding.progress.visibility = View.GONE
                        Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                    }
                    is Success<*> -> {
                        binding.progress replaceBy binding.detailsView
                        bindDetails(state.toDetails())
                    }
                }
            }
        }
        return binding.root
    }

    private fun bindDetails(core: Core) {
        activity?.title = core.serial
        missionsAdapter.submitList(listOf(getString(R.string.crew_missions_list_header).asHeader()).plus(core.launches.asBody()))
        binding.coreMissions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
            adapter = missionsAdapter
        }
    }
}