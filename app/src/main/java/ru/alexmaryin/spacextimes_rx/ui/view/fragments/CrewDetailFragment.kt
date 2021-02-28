package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.databinding.CrewDetailFragmentBinding
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CrewDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Success

@AndroidEntryPoint
class CrewDetailFragment : Fragment() {

    private val args: CrewDetailFragmentArgs by navArgs()
    private val crewViewModel: CrewDetailViewModel by viewModels()
    private lateinit var binding: CrewDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.crew_detail_fragment, container, false)
        binding.crewViewModel = crewViewModel
        binding.lifecycleOwner = this
        crewViewModel.crewId = args.crewId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crewViewModel.crew.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.detailsView.visibility = View.GONE
                }
                is Error -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(this.context, state.msg, Toast.LENGTH_SHORT).show()
                }
                is Success<*> -> {
                    binding.progress.visibility = View.GONE
                    binding.detailsView.visibility = View.VISIBLE
                }
            }
        }

        crewViewModel.crewDetails.observe(viewLifecycleOwner) {
            binding.wikiPage.loadUrl(it.wikipedia)
            activity?.title = it.name
        }
    }
}