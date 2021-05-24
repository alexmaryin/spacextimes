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
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    private val repository: SpacexDataRepository,
    private val translator: TranslatorApi
) : ViewModel() {

    var currentScreen: MainScreen = LaunchesScr
    private var needRefresh = true
    private val launchFilter = mutableSetOf(LaunchFilterType.Successfully, LaunchFilterType.Failed, LaunchFilterType.Past, LaunchFilterType.Upcoming)

    private val state = MutableSharedFlow<Result>(1)
    fun getState() = state.asSharedFlow()

    private val scrollTrigger = MutableSharedFlow<Result>(0)
    fun getScrollTrigger() = scrollTrigger.asSharedFlow()

    private val filterChange = MutableSharedFlow<Pair<Int, Int>>(0)
    fun observeFilterChange() = filterChange.asSharedFlow()

    fun changeScreen(screen: MainScreen) {
        if (screen != currentScreen || needRefresh) {
            currentScreen = screen
            needRefresh = false
            viewModelScope.launch {
                screen.readRepository(repository, translator).collect { result ->
                    state.emit(result)
                    if (screen == LaunchesScr) result.toListOf<Launch>()?.let { filterLaunches() }
                }
            }
        }
    }

    fun armRefresh() {
        needRefresh = true
        changeScreen(currentScreen)
    }

    fun toggleLaunchFilter(filter: LaunchFilterType) {
        if (filter in launchFilter) launchFilter -= filter else launchFilter += filter
        filterLaunches()
    }

    fun isFilterOn(filter: LaunchFilterType) = filter in launchFilter

    private fun filterLaunches() = viewModelScope.launch {
        val (launches, totalCount) = repository.filterLaunches(launchFilter)
        if (launches.isNotEmpty()) {
            state.emit(Success(launches))
            filterChange.emit(Pair(launches.size, totalCount))
        }
    }

    fun scrollNextLaunch() = viewModelScope.launch {
        if (LaunchFilterType.Upcoming in launchFilter) {
            repository.getNextLaunch(launchFilter)?.let { scrollTrigger.emit(Success(it)) }
        } else scrollTrigger.emit(Error("", ErrorType.UPCOMING_LAUNCHES_DESELECTED))
    }
}