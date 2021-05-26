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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.databinding.FragmentRecyclerDetailBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.ItemTypes
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CoreDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject

@AndroidEntryPoint
class CoreDetailFragment : Fragment() {

    private val coreViewModel: CoreDetailViewModel by viewModels()
    @Inject lateinit var viewHoldersManager: ViewHoldersManager
    private lateinit var binding: FragmentRecyclerDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_detail, container, false)
        binding.lifecycleOwner = this

        binding.detailsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
        }

        coreViewModel.load()

        lifecycleScope.launch {
            coreViewModel.getDetails()
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { state ->
                    when (state) {
                        is Loading -> {
                            binding.detailsList replaceBy binding.shimmerLayout.shimmer
                            binding.shimmerLayout.shimmer.startShimmer()
                            activity?.title = getString(R.string.loadingText)
                        }
                        is Error -> {
                            binding.shimmerLayout.shimmer.stopShimmer()
                            Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                        }
                        is Success<*> -> {
                            binding.shimmerLayout.shimmer.stopShimmer()
                            binding.shimmerLayout.shimmer replaceBy binding.detailsList
                            bindDetails(state.toDetails())
                        }
                    }
                }
        }
        return binding.root
    }

    private fun bindDetails(core: Core) {
        activity?.title = core.serial
        val missionsAdapter = BaseListAdapter(AdapterClickListenerById { id, itemType ->
            when (itemType) {
                ItemTypes.LAUNCH -> findNavController().navigate(CoreDetailFragmentDirections.actionShowLaunchDetails(id))
            }
        }, viewHoldersManager)
        missionsAdapter.submitList(coreViewModel.composeList(requireContext(), core))
        binding.detailsList.adapter = missionsAdapter
    }
}