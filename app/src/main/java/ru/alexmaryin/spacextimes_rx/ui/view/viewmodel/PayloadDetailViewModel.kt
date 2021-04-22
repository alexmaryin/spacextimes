package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Payload
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.LinksItem
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.OneLineItem2
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Loading
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@HiltViewModel
class PayloadDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
) : ViewModel() {

    fun getState() = repository.getPayloadById(state.get("payloadId") ?: "")
        .stateIn(viewModelScope, SharingStarted.Lazily, Loading)

    @ExperimentalTime
    fun populateDetails(res: Context, payload: Payload) = mutableListOf<HasStringId>().apply {
        with(payload) {
            add(RecyclerHeader(text = res.getString(R.string.payload_details_caption)))
            add(OneLineItem2(left = res.getString(R.string.payload_type_caption), right = typeAsString(res)))
            if (customers.isNotEmpty())
                add(OneLineItem2(
                    left = res.resources.getQuantityString(R.plurals.payload_customer_caption, customers.size),
                    right = customers.joinToString()
                ))
            if (manufacturers.isNotEmpty())
                add(OneLineItem2(
                    left = res.resources.getQuantityString(R.plurals.payload_manufacturer_caption, manufacturers.size),
                    right = manufacturers.joinToString()
                ))
            if (nationalities.isNotEmpty())
                add(OneLineItem2(
                    left = res.getString(R.string.payload_nationalities_caption),
                    right = nationalities.joinToString()
                ))
            massInKg?.let { add(OneLineItem2(left = res.getString(R.string.payload_mass_caption), right = it.toString())) }

            if (dragon.isNotEmpty()) {
                add(RecyclerHeader(text = res.resources.getQuantityString(R.plurals.assigned_capsules_caption, 1)))
                dragon.capsule?.let { add(it) }
                dragon.manifest?.let { add(LinksItem(pressKit = it)) }
                dragon.returnedMassInKg?.let { add(OneLineItem2(left = res.getString(R.string.payload_landing_mass_caption), right = it.toString())) }
                dragon.flightTime?.let { add(OneLineItem2(left = res.getString(R.string.payload_flight_time_caption), right = it.seconds.toComponents { days, hours, minutes, seconds, _ ->
                    listOfNotNull(
                        if(days > 0) res.resources.getQuantityString(R.plurals.days_count, days, days) else null,
                        if(hours > 0) res.resources.getQuantityString(R.plurals.hours_count, hours, hours) else null,
                        if(minutes > 0) res.resources.getQuantityString(R.plurals.minutes_count, minutes, minutes) else null,
                        if(seconds > 0) res.resources.getQuantityString(R.plurals.seconds_count, seconds, seconds) else null,
                    ).joinToString(" ")
                } )) }
                val landingType = dragon.waterLanding?.let { res.getString(R.string.on_water_landing) } ?: dragon.groundLanding?.let { res.getString(R.string.on_ground_landing) }
                landingType?.let { add(OneLineItem2(left = res.getString(R.string.payload_capsule_landing_caption), right = it)) }
            }

            add(RecyclerHeader(text = res.getString(R.string.orbit_details_caption)))
            orbitAsString(res)?.let { add(OneLineItem2(left = res.getString(R.string.orbit_caption), right = it)) }
            referenceSystemString(res)?.let { add(OneLineItem2(left = res.getString(R.string.reference_system_caption), right = it)) }
        }
    }
}