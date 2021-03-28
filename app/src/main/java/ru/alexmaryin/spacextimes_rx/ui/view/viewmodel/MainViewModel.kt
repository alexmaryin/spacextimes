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
import ru.alexmaryin.spacextimes_rx.data.model.Cores
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

    var currentScreen = Screen.Crew

    private val state = MutableStateFlow<Result>(Loading)
    fun getState() = state.asStateFlow()

    fun changeScreen(screen: Screen) {
        currentScreen = screen
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
            }.collect { result -> state.value = result }
        }
    }

    private val capsules = repository.getCapsules { it?.let { translateLastUpdate(it) } }

    private val cores = repository.getCores { it?.let { translateLastUpdate(it) } }.map {
                it.toListOf<Cores>()?.sortedWith(compareBy(Cores::block, Cores::serial))?.reversed()?.toSuccess() ?: it }

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