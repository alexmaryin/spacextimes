package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.databinding.CrewDetailFragmentBinding
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CrewDetailViewModel

@AndroidEntryPoint
class CrewDetailFragment : Fragment() {

    private val memberViewModel: CrewDetailViewModel by viewModels()
    private lateinit var binding: CrewDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CrewDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.crewMemberViewModel = memberViewModel
    }
}