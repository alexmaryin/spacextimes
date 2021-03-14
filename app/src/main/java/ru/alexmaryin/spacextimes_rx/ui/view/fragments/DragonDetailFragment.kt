package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.databinding.FragmentDragonDetailBinding
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.DragonDetailViewModel
import ru.alexmaryin.spacextimes_rx.utils.getCurrentLocale

@AndroidEntryPoint
class DragonDetailFragment : Fragment() {

    private val args: DragonDetailFragmentArgs by navArgs()
    private val dragonViewModel: DragonDetailViewModel by activityViewModels()
    private lateinit var binding: FragmentDragonDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dragon_detail, container, false)
        binding.dragonViewModel = dragonViewModel
        binding.lifecycleOwner = this

        dragonViewModel.state.set("dragonId", args.dragonId)
        dragonViewModel.state.set("locale", requireContext().getCurrentLocale())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}