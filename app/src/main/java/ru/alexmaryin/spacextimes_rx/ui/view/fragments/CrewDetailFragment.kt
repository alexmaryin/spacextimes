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
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.databinding.CrewDetailFragmentBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.ItemTypes
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CrewDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject

@AndroidEntryPoint
class CrewDetailFragment : Fragment() {

    private val crewViewModel: CrewDetailViewModel by viewModels()
    @Inject lateinit var viewHoldersManager: ViewHoldersManager
    private lateinit var binding: CrewDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.crew_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.crewDetails.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
        }

        crewViewModel.state.set("locale", requireContext().currentLocaleLang())
        crewViewModel.loadCrew()

        return binding.root
    }

    private fun observeState() {
        lifecycleScope.launch {
            crewViewModel.getCrewState()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun bindDetails(crew: Crew) {
        activity?.title = crew.name
        binding.crew = crew
        binding.image.setOnLongClickListener(saveByLongClickListener(requireContext(), "${crew.name}.jpg"))
        val missionsAdapter = BaseListAdapter(AdapterClickListenerById { id, itemType ->
            when (itemType) {
                ItemTypes.LAUNCH -> findNavController().navigate(CrewDetailFragmentDirections.actionShowLaunchDetails(id))
                ItemTypes.LINKS -> {
                    Toast.makeText(requireContext(), getString(R.string.open_link_announce), Toast.LENGTH_SHORT).show()
                    binding.crewDetails.openLink(id)
                }
            }
        }, viewHoldersManager)
        missionsAdapter.submitList(crewViewModel.composeDetails(requireContext(), crew))
        binding.crewDetails.adapter = missionsAdapter
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
