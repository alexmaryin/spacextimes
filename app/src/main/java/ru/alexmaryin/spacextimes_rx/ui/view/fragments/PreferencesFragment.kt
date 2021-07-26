package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import androidx.preference.SwitchPreferenceCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.collectOnFragment
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat() {

    @Inject lateinit var settings: Settings

    private inline fun <reified T> listenerOn(crossinline preference: (T) -> Unit) = Preference.OnPreferenceChangeListener { _, value ->
        if (value is T) preference(value)
        findNavController().previousBackStackEntry?.let {
            it.savedStateHandle[Settings.IS_PREFERENCES_CHANGED] = true
        }
        true
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        requireActivity().title = getString(R.string.preferencesTitle)

        val translateSwitch = findPreference<SwitchPreferenceCompat>(Settings.TRANSLATE_SWITCH)?.apply {
            onPreferenceChangeListener = listenerOn<Boolean> { value ->
                lifecycleScope.launch { settings.translateToRu(value) }
            }
        }

        val widePadSwitch = findPreference<SwitchPreferenceCompat>(Settings.WIDE_PAD_SWITCH)?.apply {
            onPreferenceChangeListener = listenerOn<Boolean> { value ->
                lifecycleScope.launch { settings.widePadding(value) }
            }
        }

        val startAtNextLaunch = findPreference<SwitchPreferenceCompat>(Settings.START_AT_NEXT_LAUNCH)?.apply {
            onPreferenceChangeListener = listenerOn<Boolean> { value ->
                lifecycleScope.launch { settings.startAtNextLaunch(value) }
            }
        }

        val refreshSeekBar = findPreference<SeekBarPreference>(Settings.REFRESH_INTERVAL_BAR)?.apply {
            onPreferenceChangeListener = listenerOn<Int> { value ->
                lifecycleScope.launch { settings.refreshInterval(value) }
            }
        }

        settings.saved.collectOnFragment(this) {
            translateSwitch?.isChecked = it.translateToRu
            refreshSeekBar?.value = it.refreshInterval / Settings.HOUR_TO_MILLIS
            widePadSwitch?.isChecked = it.widePadding
            startAtNextLaunch?.isChecked = it.startNextLaunch
        }
    }
}