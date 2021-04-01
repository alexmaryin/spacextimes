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

    val thrustersLines = arrayOf("line_1", "line_2")

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

    fun thrustersMap(res: Context, dragon: Dragon) = dragon.thrusters.map {
        mapOf(
            thrustersLines[0] to res.getString(R.string.capsule_thruster_line1, it.amount, it.pods, it.type, it.thrust.kN, it.isp),
            thrustersLines[1] to res.getString(R.string.capsule_thruster_line2, it.HotComponent, it.OxidizerComponent)
        )
    }
}