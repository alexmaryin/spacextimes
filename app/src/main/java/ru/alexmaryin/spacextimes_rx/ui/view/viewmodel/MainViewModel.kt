package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.History
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.lists.Cores
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import ru.alexmaryin.spacextimes_rx.utils.toListOf
import ru.alexmaryin.spacextimes_rx.utils.toSuccess
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    repository: SpacexDataRepository,
    private val settings: Settings,
    private val translator: TranslatorApi
) : ViewModel() {

    private suspend fun translateLastUpdate(items: List<HasLastUpdate>?) {
        if (settings.translateToRu && items != null) {
            translator.tryLoadLocalTranslate(viewModelScope.coroutineContext, items, HasLastUpdate::lastUpdate, HasLastUpdate::lastUpdateRu)
            translator.translate(viewModelScope.coroutineContext, items, HasLastUpdate::lastUpdate, HasLastUpdate::lastUpdateRu)
            translator.saveLocalTranslations(viewModelScope.coroutineContext, items, HasLastUpdate::lastUpdate, HasLastUpdate::lastUpdateRu)
        }
    }

    private suspend fun translateDescription(items: List<HasDescription>?) {
        if (settings.translateToRu && items != null) {
            translator.tryLoadLocalTranslate(viewModelScope.coroutineContext, items, HasDescription::description, HasDescription::descriptionRu)
            translator.translate(viewModelScope.coroutineContext, items, HasDescription::description, HasDescription::descriptionRu)
            translator.saveLocalTranslations(viewModelScope.coroutineContext, items, HasDescription::description, HasDescription::descriptionRu)
        }
    }

    private suspend fun translateDetails(items: List<HasDetails>?) {
        if (settings.translateToRu && items != null) {
            translator.tryLoadLocalTranslate(viewModelScope.coroutineContext, items, HasDetails::details, HasDetails::detailsRu)
            translator.translate(viewModelScope.coroutineContext, items, HasDetails::details, HasDetails::detailsRu)
            translator.saveLocalTranslations(viewModelScope.coroutineContext, items, HasDetails::details, HasDetails::detailsRu)
        }
    }

    var currentScreen = Screen.Launches
    private var needRefresh = true
    private var currentListMap = emptyMap<String, List<HasStringId>>().toMutableMap()

    private val state = MutableSharedFlow<Result>(1)
    fun getState() = state.asSharedFlow()

    fun changeScreen(screen: Screen) {
        if (screen != currentScreen || needRefresh) {
            currentScreen = screen
            needRefresh = false
            currentListMap[currentScreen.name]?.let { cachedList ->
                state.tryEmit(Success(cachedList))
            } ?: run {
                viewModelScope.launch {
                    when (screen) {
                        Screen.Capsules -> capsules
                        Screen.Cores -> cores
                        Screen.Crew -> crew
                        Screen.Dragons -> dragons
                        Screen.Rockets -> rockets
                        Screen.Launches -> launches
                        Screen.LaunchPads -> launchPads
                        Screen.LandingPads -> landingPads
                        Screen.Payloads -> payloads
                        Screen.HistoryEvents -> historyEvents
                    }.collect { result -> state.tryEmit(result) }
                }
            }
        }
    }

    private val capsules = repository.getCapsules(listOf(::translateLastUpdate))

    private val cores = repository.getCores(listOf(::translateLastUpdate)).map {
                it.toListOf<Cores>()?.sortedWith(compareBy(Cores::block, Cores::serial))?.reversed()?.toSuccess() ?: it }

    private val crew = repository.getCrew()

    private val dragons = repository.getDragons(listOf(::translateDescription))

    private val rockets = repository.getRockets(listOf(::translateDescription))

    private val launchPads = repository.getLaunchPads(listOf(::translateDetails))

    private val landingPads = repository.getLandingPads(listOf(::translateDetails))

    private val launches = repository.getLaunches(listOf(::translateDetails))

    private val payloads = repository.getPayloads()

    private val historyEvents = repository.getHistoryEvents(listOf(::translateDetails, {
        val items = it?.map { item -> item as History }
        if (settings.translateToRu && items != null) {
            translator.tryLoadLocalTranslate(viewModelScope.coroutineContext, items, History::title, History::titleRu)
            translator.translate(viewModelScope.coroutineContext, items, History::title, History::titleRu)
            translator.saveLocalTranslations(viewModelScope.coroutineContext, items, History::title, History::titleRu)
        }
    }))

    fun armRefresh() {
        needRefresh = true
        currentListMap.clear()
        changeScreen(currentScreen)
    }

    fun saveCurrentList(items: List<HasStringId>) {
        currentListMap.plusAssign(currentScreen.name to items)
    }
}