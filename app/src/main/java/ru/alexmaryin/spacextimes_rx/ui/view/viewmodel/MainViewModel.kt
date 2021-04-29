package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    private val repository: SpacexDataRepository,
    private val settings: Settings,
    private val translator: TranslatorApi,
) : ViewModel() {

    var currentScreen = Screen.Launches
    private var needRefresh = true
    private val launchFilter = mutableSetOf(LaunchFilter.Successfully, LaunchFilter.Failed, LaunchFilter.Past, LaunchFilter.Upcoming)

    private val state = MutableSharedFlow<Result>(1)
    fun getState() = state.asSharedFlow()

    private val scrollTrigger = MutableSharedFlow<Result>(0)
    fun getScrollTrigger() = scrollTrigger.asSharedFlow()

    private val filterChange = MutableSharedFlow<Pair<Int, Int>>(0)
    fun observeFilterChange() = filterChange.asSharedFlow()

    fun changeScreen(screen: Screen) {
        if (screen != currentScreen || needRefresh) {
            currentScreen = screen
            needRefresh = false
            settings.currentListMap[currentScreen.name]?.let { cachedList ->
                if (screen == Screen.Launches) filterLaunches() else state.tryEmit(Success(cachedList))
            } ?: run {
                viewModelScope.launch {
                    when (screen) {
                        Screen.Capsules -> repository.getCapsules(listOf(translator::translateLastUpdate))
                        Screen.Cores -> repository.getCores(listOf(translator::translateLastUpdate))
                        Screen.Crew -> repository.getCrew()
                        Screen.Dragons -> repository.getDragons(listOf(translator::translateDescription))
                        Screen.Rockets -> repository.getRockets(listOf(translator::translateDescription))
                        Screen.Launches -> repository.getLaunches(listOf(translator::translateDetails))
                        Screen.LaunchPads -> repository.getLaunchPads(listOf(translator::translateDetails))
                        Screen.LandingPads -> repository.getLandingPads(listOf(translator::translateDetails))
                        Screen.HistoryEvents -> repository.getHistoryEvents(listOf(translator::translateDetails, translator::translateTitle))
                    }.collect { result ->
                        state.tryEmit(result)
                        result.toListOf<HasStringId>()?.let { saveCurrentList(it) }
                        if (screen == Screen.Launches) result.toListOf<Launches>()?.let { filterLaunches() }
                    }
                }
            }
        }
    }

    fun armRefresh() {
        needRefresh = true
        settings.currentListMap.clear()
        changeScreen(currentScreen)
    }

    private fun saveCurrentList(items: List<HasStringId>) {
        settings.currentListMap += currentScreen.name to items
    }

    fun toggleLaunchFilter(filter: LaunchFilter) {
        if(filter in launchFilter) launchFilter -= filter else launchFilter += filter
        filterLaunches()?.let { filterChange.tryEmit(it) }
    }

    fun isFilterOn(filter: LaunchFilter) = filter in launchFilter

    private fun filterLaunches(): Pair<Int, Int>? {
        settings.currentListMap[Screen.Launches.name]?.let { list ->
            list.map { item -> item as Launches }.filter { launch ->
                (launch.upcoming == LaunchFilter.Upcoming in launchFilter || launch.upcoming != LaunchFilter.Past in launchFilter) &&
                        (launch.success == LaunchFilter.Successfully in launchFilter || launch.success != LaunchFilter.Failed in launchFilter)
            }.toSuccess().apply {
                state.tryEmit(this)
                return Pair(this.data.size, list.size)
            }
        }
        return null
    }

    fun scrollNextLaunch() = viewModelScope.launch {
        if (LaunchFilter.Upcoming in launchFilter) {
            settings.currentListMap[Screen.Launches.name]?.let { launches ->
                val position = launches.indexOfLast {
                    with (it as Launches) {
                        datePrecision >= DatePrecision.DAY && dateLocal > Calendar.getInstance().time
                    }
                }
                if (position >= 0) {
                    scrollTrigger.emit(Success(Pair(position, launches[position] as Launches)))
                }
            }
        } else scrollTrigger.emit(Error("", ErrorType.UPCOMING_LAUNCHES_DESELECTED))
    }
}