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
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.databinding.CrewDetailFragmentBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.asBody
import ru.alexmaryin.spacextimes_rx.ui.adapters.asHeader
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters.LaunchesAdapter
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CrewDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.*

@AndroidEntryPoint
class CrewDetailFragment : Fragment() {

    private val args: CrewDetailFragmentArgs by navArgs()
    private val crewViewModel: CrewDetailViewModel by viewModels()
    private lateinit var binding: CrewDetailFragmentBinding
    private val missionsAdapter = LaunchesAdapter(AdapterClickListenerById {})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.crew_detail_fragment, container, false)
        binding.lifecycleOwner = this
        binding.wikiFrame.wikiPage.attachProgressAndRootView(binding.wikiFrame.wikiProgress, binding.detailsView)

        crewViewModel.state.set("crewId", args.crewId)
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
                        binding.detailsView replaceBy binding.wikiFrame.progress
                        activity?.title = getString(R.string.loadingText)
                    }
                    is Error -> {
                        binding.wikiFrame.progress.visibility = View.GONE
                        Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                    }
                    is Success<*> -> {
                        binding.wikiFrame.progress replaceBy binding.detailsView
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
        binding.wikiButton.setOnClickListener { binding.wikiFrame.wikiPage.loadUrl(crew.wikiLocale ?: crew.wikipedia ?: "") }
        binding.image.setOnLongClickListener(saveByLongClickListener(requireContext(), "${crew.name}.jpg"))
        if (crew.launches.isNotEmpty()) {
            missionsAdapter.submitList(listOf(getString(R.string.crew_missions_list_header).asHeader()).plus(crew.launches.asBody()))
            binding.crewMissions.apply {
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
                adapter = missionsAdapter
            }
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
