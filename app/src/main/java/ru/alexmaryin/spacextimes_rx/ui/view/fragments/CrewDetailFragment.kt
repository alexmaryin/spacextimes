package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.databinding.CrewDetailFragmentBinding
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CrewDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.*

@AndroidEntryPoint
class CrewDetailFragment : Fragment() {

    private val args: CrewDetailFragmentArgs by navArgs()
    private val crewViewModel: CrewDetailViewModel by activityViewModels()
    private lateinit var binding: CrewDetailFragmentBinding

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

    private fun observeState() = lifecycleScope.launchWhenResumed {
        crewViewModel.getCrewState().collect { state ->
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun bindDetails(crew: Crew) {
        activity?.title = crew.name
        binding.crew = crew
        binding.wikiButton.setOnClickListener { binding.wikiFrame.wikiPage.loadUrl(crew.wikiLocale ?: crew.wikipedia ?: "") }
        binding.image.setOnLongClickListener(saveByLongClickListener(requireContext(), "${crew.name}.jpg"))
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
