package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        requireActivity().title = getString(R.string.preferencesTitle)

        settings.saved.collectOnFragment(this) {

            findPreference<SwitchPreferenceCompat>(Settings.TRANSLATE_SWITCH)?.apply {
                isChecked = it.translateToRu
                setOnPreferenceChangeListener { _, value ->
                    lifecycleScope.launch { settings.translateToRu(value as Boolean) }
                    findNavController().previousBackStackEntry?.let {
                        it.savedStateHandle[Settings.IS_PREFERENCES_CHANGED] = true
                    }
                    true
                }
            }

            findPreference<SeekBarPreference>(Settings.REFRESH_INTERVAL_BAR)?.apply {
                value = it.refreshInterval / Settings.HOUR_TO_MILLIS
                setOnPreferenceChangeListener { _, value ->
                    lifecycleScope.launch { settings.refreshInterval(value as Int) }
                    findNavController().previousBackStackEntry?.let {
                        it.savedStateHandle[Settings.IS_PREFERENCES_CHANGED] = true
                    }
                    true
                }
            }
        }
    }
}