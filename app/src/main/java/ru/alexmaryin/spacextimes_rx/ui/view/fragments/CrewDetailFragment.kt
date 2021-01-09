package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CrewDetailViewModel

class CrewDetailFragment : Fragment() {

    private lateinit var member: Crew

    companion object {
        fun newInstance(_member: Crew) = CrewDetailFragment().apply {
            member = _member
        }
    }

    private lateinit var viewModel: CrewDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.crew_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CrewDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}