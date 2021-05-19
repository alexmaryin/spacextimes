package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.wiki.localizeWiki
import ru.alexmaryin.spacextimes_rx.data.model.Rocket
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.OrbitType
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.LinksItem
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.OneLineItem2
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.TwoStringsItem
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class RocketDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    val repository: SpacexDataRepository,
        ) : ViewModel() {

    private val rocketState = MutableStateFlow<Result>(Loading)
    fun getRocketState() = rocketState.asStateFlow()

    fun loadRocket() = viewModelScope.launch {
        repository.getRocketById(state.get("rocketId") ?: "")
            .localizeWiki<Rocket>(state.get("locale") ?: "en")
            .collect { result -> rocketState.value = result }
    }

    fun composeDetails(res: Context, rocket: Rocket) = mutableListOf<HasStringId>().apply {
        add(TwoStringsItem(caption = res.getString(R.string.rocket_size_caption), details = res.getString(
            R.string.rocket_size_string,
            rocket.height.meters,
            rocket.diameter.meters,
            rocket.mass.kg / 1000
        )))
        add(OneLineItem2(left = res.getString(R.string.stages_count_string), right = "${rocket.stages}"))
        if(rocket.boosters > 0) add(OneLineItem2(left = res.getString(R.string.boosters_count_string), right = "${rocket.boosters}"))

        add(OneLineItem2(left = res.getString(R.string.rocket_success_rate_string), right = "${rocket.successRate.roundToInt()}%"))
        add(OneLineItem2(
            left = res.getString(R.string.rocket_cost_launch_string),
            right = NumberFormat.getCurrencyInstance(Locale.US).format(rocket.costPerLaunch))
        )
        add(OneLineItem2(left = res.getString(R.string.first_flight_caption), right = rocket.firstFlightStr(res)))
        if (rocket.landingLegs.number > 0) {
            add(OneLineItem2(
                left = res.getString(R.string.landing_legs_caption),
                right = res.getString(R.string.landing_legs_str, rocket.landingLegs.number, rocket.landingLegs.material)))
        }

        add(RecyclerHeader(text = res.getString(R.string.rocket_engines_caption)))
        with(rocket.engines) {
            add(TwoStringsItem(
                caption = res.resources.getQuantityString(R.plurals.rocket_engines_string, number, number, type,
                    version.ifBlank { "beta" }),
                details = res.getString(R.string.capsule_thruster_line2, hotComponent, oxidizerComponent)
            ))
            add(TwoStringsItem(
                caption = res.getString(R.string.rocket_isp_caption),
                details = res.getString(R.string.rocket_isp_string, isp.seaLevel, isp.vacuum)
            ))
            add(TwoStringsItem(
                caption = res.getString(R.string.rocket_thrust_caption),
                details = res.getString(R.string.rocket_thrust_string, thrustSeaLevel.kN, thrustVacuum.kN)
            ))
            add(TwoStringsItem(
                caption = res.getString(R.string.engine_twr_string, thrustToWeight),
                details = res.getString(R.string.engine_loss_string, engineLossMax)
            ))
        }

        add(RecyclerHeader(text = res.getString(R.string.first_stage_caption)))
        with(rocket.firstStage) {
            add(OneLineItem2(
                left = res.getString(R.string.engines_numb_str, engines),
                right = res.getString(if(reusable) R.string.reusable_stage_str else R.string.expendable_stage_str)
            ))
            add(OneLineItem2(
                left = res.getString(R.string.thrust_sl_caption),
                right = res.getString(R.string.thrust_str, thrustSeaLevel.kN)
            ))
            add(OneLineItem2(
                left = res.getString(R.string.thrust_vac_caption),
                right = res.getString(R.string.thrust_str, thrustVacuum.kN)
            ))
            add(OneLineItem2(
                left = res.getString(R.string.stage_fuel_amount_str, fuelAmount ?: 0f),
                right = res.getString(R.string.stage_burn_time_str, burnTime ?: 0)
            ))
        }

        add(RecyclerHeader(text = res.getString(R.string.second_stage_caption)))
        with(rocket.secondStage) {
            add(OneLineItem2(
                left = res.getString(R.string.engines_numb_str, engines),
                right = res.getString(if(reusable) R.string.reusable_stage_str else R.string.expendable_stage_str)
            ))
            add(OneLineItem2(
                left = res.getString(R.string.thrust_vac_caption),
                right = res.getString(R.string.thrust_str, thrust.kN)
            ))
            add(OneLineItem2(
                left = res.getString(R.string.stage_fuel_amount_str, fuelAmount ?: 0f),
                right = res.getString(R.string.stage_burn_time_str, burnTime ?: 0)
            ))
            if (payloads.compositeFairing.height.meters != null) {
                add(TwoStringsItem(
                    caption = res.getString(R.string.fairing_caption),
                    details = res.getString(R.string.fairing_details_str,
                        payloads.compositeFairing.height.meters,
                        payloads.compositeFairing.diameter.meters)
                ))
            }
        }

        add(RecyclerHeader(text = res.getString(R.string.max_payload_caption)))
        rocket.payloadWeights.forEach {
            add(OneLineItem2(
                left = when(it.id) {
                    OrbitType.LOW_EARTH -> res.getString(R.string.leo_caption)
                    OrbitType.GEOSYNCHRONOUS -> res.getString(R.string.gso_caption)
                    OrbitType.MARS -> res.getString(R.string.mars_caption)
                    OrbitType.PLUTO -> res.getString(R.string.pluto_caption)
                    OrbitType.MOON -> res.getString(R.string.moon_caption)
                },
                right = res.getString(R.string.weight_string, it.kg)
            ))
        }

        add(RecyclerHeader(text = res.getString(R.string.links_string)))
        add(LinksItem(wiki = rocket.wikiLocale ?: rocket.wikipedia))
    }
}