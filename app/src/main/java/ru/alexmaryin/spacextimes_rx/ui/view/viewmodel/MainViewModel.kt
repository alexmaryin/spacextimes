package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.toListOf
import ru.alexmaryin.spacextimes_rx.utils.toSuccess
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    repository: SpacexDataRepository,
    private val settings: Settings,
    private val translator: TranslatorApi
) : ViewModel() {

    private suspend fun translateLastUpdate(items: List<HasLastUpdate>) {
        if (settings.translateToRu)
            translator.translate(viewModelScope.coroutineContext, items, HasLastUpdate::lastUpdate, HasLastUpdate::lastUpdateRu)
    }

    private suspend fun translateDescription(items: List<HasDescription>) {
        if (settings.translateToRu)
            translator.translate(viewModelScope.coroutineContext, items, HasDescription::description, HasDescription::descriptionRu)
    }

    private suspend fun translateDetails(items: List<HasDetails>) {
        if (settings.translateToRu)
            translator.translate(viewModelScope.coroutineContext, items, HasDetails::details, HasDetails::detailsRu)
    }

    private val state = MutableStateFlow<Result>(Loading)
    fun getState() = state.asStateFlow()

    var currentScreen = Screen.Crew

    fun changeScreen(screen: Screen) {
        viewModelScope.launch {
            when (screen) {
                Screen.Capsules -> capsules.collect { result -> state.value = result }
                Screen.Cores -> cores.collect { result -> state.value = result }
                Screen.Crew -> crew.collect { result -> state.value = result }
                Screen.Dragons -> dragons.collect { result -> state.value = result }
                Screen.Rockets -> rockets.collect { result -> state.value = result }
                Screen.Launches -> launches.collect { result -> state.value = result }
                Screen.LaunchPads -> launchPads.collect { result -> state.value = result }
                Screen.LandingPads -> landingPads.collect { result -> state.value = result }
            }
        }
        currentScreen = screen
    }

    private val capsules = repository.getCapsules { it?.let { translateLastUpdate(it) } }

    private val cores = repository.getCores { it?.let { translateLastUpdate(it) } }.map {
                it.toListOf<Core>()?.sortedWith(compareBy(Core::block, Core::serial))?.reversed()?.toSuccess() ?: it
            }

    private val crew = repository.getCrew()

    private val dragons = repository.getDragons { it?.let { translateDescription(it) } }

    private val rockets = repository.getRockets { it?.let { translateDescription(it) } }

    private val launchPads = repository.getLaunchPads { it?.let { translateDetails(it) } }

    private val landingPads = repository.getLandingPads { it?.let { translateDetails(it) } }

    private val launches = repository.getLaunches()

    fun armRefresh() {
        changeScreen(currentScreen)
    }
}