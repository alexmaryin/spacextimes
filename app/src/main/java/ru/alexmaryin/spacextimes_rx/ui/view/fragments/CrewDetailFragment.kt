package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
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

        createWebClient(binding.wikiPage)

        crewViewModel.state.set("crewId", args.crewId)
        crewViewModel.state.set("locale", getCurrentLocale())
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

        crewViewModel.crewDetails.observe(viewLifecycleOwner) { crewMember ->
            binding.wikiButton.setOnClickListener { binding.wikiPage.loadUrl(crewMember.wikiLocale ?: crewMember.wikipedia) }
            activity?.title = crewMember.name
        }
    }

    @Suppress("DEPRECATION")
    private fun getCurrentLocale() = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) requireContext().resources.configuration.locales[0].language
        else requireContext().resources.configuration.locale.language

    private fun createWebClient(web: WebView) {
        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.wikiProgress.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.wikiProgress.visibility = View.GONE
            }
        }

        web.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                when (newProgress) {
                    100 -> binding.wikiPage crossFadeWith binding.detailsView
                    else -> binding.wikiProgress.progress = newProgress
                }
            }
        }
    }
}
