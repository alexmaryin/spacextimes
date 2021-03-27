package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.alexmaryin.spacextimes_rx.R
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
        binding.crewViewModel = crewViewModel
        binding.lifecycleOwner = this
        binding.wikiFrame.wikiPage.attachProgressAndRootView(binding.wikiFrame.wikiProgress, binding.detailsView)

        crewViewModel.state.set("crewId", args.crewId)
        crewViewModel.state.set("locale", requireContext().currentLocaleLang())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crewViewModel.crew.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> {
                    binding.wikiFrame.progress.visibility = View.VISIBLE
                    binding.detailsView.visibility = View.GONE
                    activity?.title = getString(R.string.loadingText)
                }
                is Error -> {
                    binding.wikiFrame.progress.visibility = View.GONE
                    Toast.makeText(this.context, state.msg, Toast.LENGTH_SHORT).show()
                }
                is Success<*> -> {
                    binding.wikiFrame.progress.visibility = View.GONE
                    binding.detailsView.visibility = View.VISIBLE
                    activity?.title = crewViewModel.getTitle()
                }
            }
        }

        crewViewModel.crewDetails.observe(viewLifecycleOwner) { crewMember ->
            binding.wikiButton.setOnClickListener { binding.wikiFrame.wikiPage.loadUrl(crewMember.wikiLocale ?: crewMember.wikipedia ?: "") }
        }

        binding.image.setOnLongClickListener { image ->
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.saving_title_string))
                .setMessage(getString(R.string.save_image_dialog_string))
                .setPositiveButton(getString(R.string.agreeText)) { dialog, _ ->
                    image.saveToStorage(requireContext(), "${crewViewModel.crewDetails.value!!.name}.jpg")?.let {
                        requireContext().notifyOnSavedPhoto(it)
                    } ?: Toast.makeText(requireContext(), getString(R.string.failed_image_save_string), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.cancelText)) { dialog, _ -> dialog.dismiss() }
                .show()
            true
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
