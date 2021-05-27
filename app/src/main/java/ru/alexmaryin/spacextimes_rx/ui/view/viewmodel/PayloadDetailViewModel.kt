package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.data.model.Payload
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.LinksItem
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.OneLineItem2
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.prettifyMinutesPeriod
import ru.alexmaryin.spacextimes_rx.utils.prettifySecondsPeriod
import java.text.DateFormat
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@HiltViewModel
class PayloadDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
) : ViewModel() {

    private val payloadDetails = MutableStateFlow<Result>(Loading)
    fun getDetails() = payloadDetails.asStateFlow()

    fun loadPayload() = viewModelScope.launch {
        repository.getPayloadById(state.get("payloadId") ?: "").collect { payloadDetails.emit(it) }
    }

    @ExperimentalTime
    fun populateDetails(res: Context, payload: Payload) = mutableListOf<HasStringId>().apply {
        with(payload) {

            add(RecyclerHeader(text = res.getString(R.string.payload_details_caption)))
            typeAsString(res)?.let { add(OneLineItem2(left = res.getString(R.string.payload_type_caption), right = it)) }
            if (customers.isNotEmpty())
                add(
                    OneLineItem2(
                        left = res.resources.getQuantityString(R.plurals.payload_customer_caption, customers.size),
                        right = customers.joinToString()
                    )
                )
            if (manufacturers.isNotEmpty())
                add(
                    OneLineItem2(
                        left = res.resources.getQuantityString(R.plurals.payload_manufacturer_caption, manufacturers.size),
                        right = manufacturers.joinToString()
                    )
                )
            if (nationalities.isNotEmpty())
                add(
                    OneLineItem2(
                        left = res.getString(R.string.payload_nationalities_caption),
                        right = nationalities.joinToString()
                    )
                )
            massInKg?.let { add(OneLineItem2(left = res.getString(R.string.payload_mass_caption), right = res.getString(R.string.weight_string, it))) }

            if (dragon.isNotEmpty()) {
                add(RecyclerHeader(text = res.resources.getQuantityString(R.plurals.assigned_capsules_caption, 1)))
                dragon.capsule?.let { add(it) }
                dragon.manifest?.let { add(LinksItem(pressKit = it)) }
                dragon.returnedMassInKg?.let { add(OneLineItem2(left = res.getString(R.string.payload_landing_mass_caption), right = res.getString(R.string.weight_string, it))) }
                dragon.flightTime?.let { add(OneLineItem2(left = res.getString(R.string.payload_flight_time_caption), right = it.prettifySecondsPeriod(res))) }
                val landingType = dragon.waterLanding?.let { res.getString(R.string.on_water_landing) } ?: dragon.groundLanding?.let { res.getString(R.string.on_ground_landing) }
                landingType?.let { add(OneLineItem2(left = res.getString(R.string.payload_capsule_landing_caption), right = it)) }
            }

            add(RecyclerHeader(text = res.getString(R.string.orbit_details_caption)))
            orbitAsString(res)?.let { add(OneLineItem2(left = res.getString(R.string.orbit_caption), right = it)) }
            referenceSystemString(res)?.let { add(OneLineItem2(left = res.getString(R.string.reference_system_caption), right = it)) }
            apoapsis?.let { add(OneLineItem2(left = res.getString(R.string.apoapsis_caption), right = res.getString(R.string.distance_string, it))) }
            periapsis?.let { add(OneLineItem2(left = res.getString(R.string.periapsis_caption), right = res.getString(R.string.distance_string, it))) }
            period?.let { add(OneLineItem2(left = res.getString(R.string.period_caption), right = it.toDouble().prettifyMinutesPeriod(res))) }
            lifeSpan?.let { add(OneLineItem2(left = res.getString(R.string.life_span_caption), right = res.resources.getQuantityString(R.plurals.years_count, it.toInt(), it.toInt()))) }

            if (isOrbitDataPresent) {
                add(RecyclerHeader(text = res.getString(R.string.orbit_elements_caption)))
                eccentricity?.let { add(OneLineItem2(left = res.getString(R.string.eccentricity_caption), right = "%.4f".format(it))) }
                semiAxis?.let { add(OneLineItem2(left = res.getString(R.string.semimajor_axis_caption), right = res.getString(R.string.distance_string, it))) }
                inclination?.let { add(OneLineItem2(left = res.getString(R.string.inclination_caption), right = res.getString(R.string.degrees_string, it))) }
                longitude?.let { add(OneLineItem2(left = res.getString(R.string.longtitude_AN_caption), right = res.getString(R.string.degrees_string, it))) }
                pericenterArg?.let { add(OneLineItem2(left = res.getString(R.string.periapsis_arg_caption), right = res.getString(R.string.degrees_string, it))) }
                rightAscension?.let { add(OneLineItem2(left = res.getString(R.string.right_ascension_caption), right = res.getString(R.string.degrees_string, it))) }
                meanAnomaly?.let { add(OneLineItem2(left = res.getString(R.string.mean_anomaly_caption), right = res.getString(R.string.distance_string, it))) }
                meanMotion?.let { add(OneLineItem2(left = res.getString(R.string.mean_motion_caption), right = "%.4f".format(it))) }
                epoch?.let { add(OneLineItem2(left = res.getString(R.string.epoch_caption), right = DateFormat.getDateTimeInstance(DateFormat.LONG, TimeFormat.CLOCK_24H).format(it))) }
            }
        }
    }
}