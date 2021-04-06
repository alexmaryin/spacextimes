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
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderApi
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.TwoStringsItem
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import javax.inject.Inject

@HiltViewModel
class DragonDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
    private val wikiApi: WikiLoaderApi,
) : ViewModel() {

    private val dragonState = MutableStateFlow<Result>(Loading)
    fun getDragonState() = dragonState.asStateFlow()

    fun loadDragon() = viewModelScope.launch {
        repository.getDragonById(state.get("dragonId") ?: "").apply {
            if (this is Success<*>) (data as Dragon).wikiLocale = localeWikiUrl(data.wikipedia)
        }.collect { result -> dragonState.value = result }
    }

    private suspend fun localeWikiUrl(enUrl: String?) = enUrl?.let {
        wikiApi.getLocaleLink(enUrl, state.get("locale") ?: "en")
    }

    fun composeDetails(res: Context, dragon: Dragon) = mutableListOf<HasStringId>().apply {
        add(RecyclerHeader(text = res.getString(R.string.spacecraft_characteristics_string)))

        add(TwoStringsItem(
            caption = res.getString(R.string.dragon_payload_caption),
            details = res.getString(R.string.dragon_payload_line_string,
                dragon.launchPayloadMass.kg / 1000,
                dragon.launchPayloadVolume.inMeters,
                dragon.returnPayloadMass.kg / 1000,
                dragon.returnPayloadVolume.inMeters)
        ))

        add(TwoStringsItem(
            caption = res.getString(R.string.dragon_size_caption),
            details = res.getString(R.string.dragon_size_string,
                dragon.heightWithTrunk.meters,
                dragon.diameter.meters,
                dragon.pressurizedCapsule.payload.inMeters
            )
        ))

        add(TwoStringsItem(
            caption = res.getString(R.string.heat_shield_caption),
            details = res.getString(R.string.heat_shield_string,
                dragon.heatShield.size,
                dragon.heatShield.material,
                dragon.heatShield.temperature,
                dragon.heatShield.developPartner
            )
        ))

        if(dragon.crewCapacity > 0)
            add(TwoStringsItem(
                caption = res.getString(R.string.crew_capacity_string),
                details = "\uD83D\uDC64".repeat(dragon.crewCapacity)))

        add(RecyclerHeader(text = res.getString(R.string.list_thrusters_string)))
        addAll(dragon.thrusters.map {
            TwoStringsItem(
                caption =  res.getString(R.string.capsule_thruster_line1, it.amount, it.pods, it.type, it.thrust.kN, it.isp),
                details = res.getString(R.string.capsule_thruster_line2, it.HotComponent, it.OxidizerComponent)
            )}
        )
    }
}