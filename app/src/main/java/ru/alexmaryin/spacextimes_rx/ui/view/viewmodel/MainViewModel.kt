package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    private val repository: SpacexDataRepository,
    private val translator: TranslatorApi,
    private val settings: Settings,
) : ViewModel() {

    var currentScreen: MainScreen = Launches
    private var needRefresh = true

    val getFilterIfAvailable get() = if( currentScreen.filter != EmptyFilter) currentScreen.filter else null

    val isFilterAvailable get() = currentScreen.filter != EmptyFilter

    private val state = MutableSharedFlow<Result>(1)
    fun getState() = state.asSharedFlow()

    private val scrollTrigger = MutableSharedFlow<Boolean>(0)
    fun getScrollTrigger() = scrollTrigger.asSharedFlow()

    fun changeScreen(screen: MainScreen) {
        if (screen != currentScreen || needRefresh) {
            currentScreen = screen
            needRefresh = false
            viewModelScope.launch {
                screen.readRepository(repository, translator).collect { result ->
                    state.emit(result)
                }
            }
        }
    }

    fun armRefresh() {
        needRefresh = true
        changeScreen(currentScreen)
    }

    fun startSynchronization() {
        settings.armedSynchronize = true
        armRefresh()
    }

    fun getNextLaunchPosition(launches: List<Launch>) = repository.getNextLaunch(launches).nullIfNegative()

    fun scrollNextLaunch() = viewModelScope.launch {
        scrollTrigger.emit(currentScreen.filter.isFilterOn("Upcoming"))
    }
}