package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.LaunchPad
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.OneLineItem2
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.TwoStringsItem
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import javax.inject.Inject

@HiltViewModel
class LaunchPadDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    val repository: SpacexDataRepository,
    private val translator: TranslatorApi,
) : ViewModel() {

    private val padState = MutableStateFlow<Result>(Loading)
    fun getPadState() = padState.asStateFlow()

    fun loadLaunchPad() = viewModelScope.launch {
        repository.getLaunchPadById(state.get("launchPadId") ?: "")
            .map { state ->
                if(state is Success<*>) { translator.translateDetails(listOf(state.data as LaunchPad)) }
                state
            }.collect { result -> padState.value = result }
    }

    fun composeDetails(res: Context, launchPad: LaunchPad) = mutableListOf<HasStringId>().apply {
        add(
            OneLineItem2(
                left = res.getString(R.string.status_pad_caption),
                right = res.getString(
                    when (launchPad.status) {
                        PadStatus.UNKNOWN -> R.string.unknownText
                        PadStatus.ACTIVE -> R.string.activeText
                        PadStatus.INACTIVE -> R.string.inactiveText
                        PadStatus.RETIRED -> R.string.retiredText
                        PadStatus.LOST -> R.string.destroyedText
                        PadStatus.UNDER_CONSTRUCTION -> R.string.underConstructionText
                    }
                )
            )
        )

        add(
            OneLineItem2(
                left = res.getString(R.string.launch_statistic_caption),
                right = res.getString(R.string.statistic_text, launchPad.launchSuccesses, launchPad.launchAttempts)
            )
        )

        launchPad.details?.let { details ->
            add(
                TwoStringsItem(
                    caption = res.getString(R.string.details_caption),
                    details = launchPad.detailsRu ?: details
                )
            )
        }

        if (launchPad.rockets.isNotEmpty()) {
            add(RecyclerHeader(text = res.getString(R.string.rockets_launched_caption)))
            addAll(launchPad.rockets)
        }

        if (launchPad.launches.isNotEmpty()) {
            add(RecyclerHeader(text = res.getString(R.string.pad_launches_caption)))
            addAll(launchPad.launches)
        }
    }
}