package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.LandingPad
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.CarouselItem
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.OneLineItem2
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.TwoStringsItem
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import javax.inject.Inject

@HiltViewModel
class LandingPadDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    val repository: SpacexDataRepository,
    private val translator: TranslatorApi,
    private val settings: Settings,
) : ViewModel() {

    private val padState = MutableStateFlow<Result>(Loading)
    fun getPadState() = padState.asStateFlow()

    fun loadLandingPad() = viewModelScope.launch {
        translator.run {
            repository.getLandingPadById(state.get("landingPadId") ?: "")
                .translateDetails()
                .collect { result -> padState.emit(result) }
        }
    }

    fun composeDetails(res: Context, landingPad: LandingPad) = mutableListOf<HasStringId>().apply {

        if (landingPad.launches.isEmpty() && landingPad.landingAttempts + landingPad.landingSuccesses > 0)  viewModelScope.launch {
            Log.d("REPOSITORY", "Arm internet refresh for landing pads because ${landingPad.launches.size} != ${landingPad.landingAttempts}")
            settings.armSynchronize = true
            loadLandingPad()
        } else {

            landingPad.images.large.ifEmpty { landingPad.images.small.ifEmpty { null } }?.let {
                add(CarouselItem(images = it, prefix = landingPad.name!!))
            }

            add(
                OneLineItem2(
                    left = res.getString(R.string.status_pad_caption),
                    right = res.getString(
                        when (landingPad.status) {
                            PadStatus.UNKNOWN -> R.string.unknownText
                            PadStatus.ACTIVE -> R.string.activeText
                            PadStatus.INACTIVE -> R.string.inactiveText
                            PadStatus.RETIRED -> R.string.retiredText
                            PadStatus.LOST -> R.string.destroyedText
                            PadStatus.UNDER_CONSTRUCTION -> R.string.underConstructionText
                            else -> R.string.unknownText
                        }
                    )
                )
            )

            add(
                OneLineItem2(
                    left = res.getString(R.string.landing_pad_type_caption),
                    right = res.getString(
                        when (landingPad.type) {
                            LandingPadType.LANDING_SITE -> R.string.site_text
                            LandingPadType.DRONE_SHIP -> R.string.drone_text
                        }
                    )
                )
            )

            add(
                OneLineItem2(
                    left = res.getString(R.string.landing_statistic_caption),
                    right = res.getString(R.string.statistic_text, landingPad.landingSuccesses, landingPad.landingAttempts)
                )
            )

            landingPad.details?.let { details ->
                add(
                    TwoStringsItem(
                        caption = res.getString(R.string.details_caption),
                        details = landingPad.detailsRu ?: details
                    )
                )
            }

            if (landingPad.launches.isNotEmpty()) {
                add(RecyclerHeader(text = res.getString(R.string.missions_list_header)))
                addAll(landingPad.launches)
            }
        }
    }
}