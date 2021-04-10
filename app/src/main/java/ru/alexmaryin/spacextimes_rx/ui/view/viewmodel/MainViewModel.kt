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
    private val translator: TranslatorApi,
) : ViewModel() {

    var currentScreen = Screen.Launches
    private var needRefresh = true

    private val state = MutableSharedFlow<Result>(1)
    fun getState() = state.asSharedFlow()

    fun changeScreen(screen: Screen) {
        if (screen != currentScreen || needRefresh) {
            currentScreen = screen
            needRefresh = false
            settings.currentListMap[currentScreen.name]?.let { cachedList ->
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

    private val capsules = repository.getCapsules(listOf(translator::translateLastUpdate))

    private val cores = repository.getCores(listOf(translator::translateLastUpdate)).map {
                it.toListOf<Cores>()?.sortedWith(compareBy(Cores::block, Cores::serial))?.reversed()?.toSuccess() ?: it }

    private val crew = repository.getCrew()

    private val dragons = repository.getDragons(listOf(translator::translateDescription))

    private val rockets = repository.getRockets(listOf(translator::translateDescription))

    private val launchPads = repository.getLaunchPads(listOf(translator::translateDetails))

    private val landingPads = repository.getLandingPads(listOf(translator::translateDetails))

    private val launches = repository.getLaunches(listOf(translator::translateDetails))

    private val payloads = repository.getPayloads()

    private val historyEvents = repository.getHistoryEvents(listOf(translator::translateDetails, translator::translateTitle))

    fun armRefresh() {
        needRefresh = true
        settings.currentListMap.clear()
        changeScreen(currentScreen)
    }

    fun saveCurrentList(items: List<HasStringId>) {
        settings.currentListMap.plusAssign(currentScreen.name to items)
    }
}