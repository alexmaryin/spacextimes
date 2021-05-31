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
import ru.alexmaryin.spacextimes_rx.di.HOUR_TO_MILLIS
import ru.alexmaryin.spacextimes_rx.di.SettingsRepository
import ru.alexmaryin.spacextimes_rx.utils.collectOnFragment
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var settings: SettingsRepository

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        requireActivity().title = getString(R.string.preferencesTitle)

        settings.saved.collectOnFragment(this) {

            findPreference<SwitchPreferenceCompat>("translate_to_ru")?.apply {
                isChecked = it.translateToRu
                setOnPreferenceChangeListener { _, value ->
                    lifecycleScope.launch { settings.translateToRu(value as Boolean) }
                    findNavController().previousBackStackEntry?.let {
                        it.savedStateHandle["preferences_changed"] = true
                    }
                    true
                }
            }

            findPreference<SeekBarPreference>("refresh_interval")?.apply {
                value = it.refreshInterval / HOUR_TO_MILLIS
                setOnPreferenceChangeListener { _, value ->
                    lifecycleScope.launch { settings.refreshInterval(value as Int) }
                    findNavController().previousBackStackEntry?.let {
                        it.savedStateHandle["preferences_changed"] = true
                    }
                    true
                }
            }
        }
    }
}