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
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderApi
import ru.alexmaryin.spacextimes_rx.data.model.Rocket
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.OneLineItem2
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.TwoStringsItem
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class RocketDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    val repository: SpacexDataRepository,
    private val wikiApi: WikiLoaderApi,
        ) : ViewModel() {

    private val rocketState = MutableStateFlow<Result>(Loading)
    fun getRocketState() = rocketState.asStateFlow()

    fun loadRocket() = viewModelScope.launch {
        repository.getRocketById(state.get("rocketId") ?: "")
            .map { if (it is Success<*>) (it.data as Rocket).wikiLocale = localeWikiUrl(it.data.wikipedia); it }
            .collect { result -> rocketState.value = result }
    }

    private suspend fun localeWikiUrl(enUrl: String?) = enUrl?.let {
        wikiApi.getLocaleLink(enUrl, state.get("locale") ?: "en")
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

        add(RecyclerHeader(text = res.getString(R.string.rocket_engines_caption)))
        with(rocket.engines) {
            add(TwoStringsItem(
                caption = res.resources.getQuantityString(R.plurals.rocket_engines_string, number, number, type,
                    if(version.isNotBlank()) version else "beta"),
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


    }
}