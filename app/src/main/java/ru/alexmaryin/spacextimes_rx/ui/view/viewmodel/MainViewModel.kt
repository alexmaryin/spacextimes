package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.chip.Chip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.lists.Cores
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import ru.alexmaryin.spacextimes_rx.utils.toListOf
import ru.alexmaryin.spacextimes_rx.utils.toSuccess
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    repository: SpacexDataRepository,
    private val settings: Settings,
    private val translator: TranslatorApi,
) : ViewModel() {

    var currentScreen = Screen.Launches
    private var needRefresh = true
    private val launchFilter = mutableSetOf(LaunchFilter.Successfully, LaunchFilter.Failed, LaunchFilter.Past, LaunchFilter.Upcoming)

    private val state = MutableSharedFlow<Result>(1)
    fun getState() = state.asSharedFlow()

    val scrollTrigger = MutableSharedFlow<Pair<Int, Launches>>(1)

    fun changeScreen(screen: Screen) {
        if (screen != currentScreen || needRefresh) {
            currentScreen = screen
            needRefresh = false
            settings.currentListMap[currentScreen.name]?.let { cachedList ->
                if (screen == Screen.Launches) filterLaunches() else state.tryEmit(Success(cachedList))
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
                        Screen.HistoryEvents -> historyEvents
                    }.collect { result ->
                        state.tryEmit(result)
                        result.toListOf<HasStringId>()?.let { saveCurrentList(it) }
                        if (screen == Screen.Launches) result.toListOf<Launches>()?.let { filterLaunches() }
                    }
                }
            }
        }
    }

    private val capsules = repository.getCapsules(listOf(translator::translateLastUpdate))

    private val cores = repository.getCores(listOf(translator::translateLastUpdate)).map {
        it.toListOf<Cores>()?.sortedWith(compareBy(Cores::block, Cores::serial))?.reversed()?.toSuccess() ?: it
    }

    private val crew = repository.getCrew()

    private val dragons = repository.getDragons(listOf(translator::translateDescription))

    private val rockets = repository.getRockets(listOf(translator::translateDescription))

    private val launchPads = repository.getLaunchPads(listOf(translator::translateDetails))

    private val landingPads = repository.getLandingPads(listOf(translator::translateDetails))

    private val launches = repository.getLaunches(listOf(translator::translateDetails))

    private val historyEvents = repository.getHistoryEvents(listOf(translator::translateDetails, translator::translateTitle))

    fun armRefresh() {
        needRefresh = true
        settings.currentListMap.clear()
        changeScreen(currentScreen)
    }

    private fun saveCurrentList(items: List<HasStringId>) {
        settings.currentListMap.plusAssign(currentScreen.name to items)
    }

    fun toggleLaunchFilter(view: View, filter: LaunchFilter) {
        if ((view as Chip).isChecked) launchFilter += filter else launchFilter -= filter

        with(view.parent as View) {
            postDelayed({ visibility = View.GONE }, 1000)
        }

        filterLaunches()?.let { (shown, total) ->
            Toast.makeText(view.context, view.context.getString(R.string.filtered_launches_toast, shown, total), Toast.LENGTH_LONG).show()
        }
    }

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

    @ExperimentalCoroutinesApi
    fun scrollNextLaunch(view: View) {
        settings.currentListMap[Screen.Launches.name]?.let { launches ->
            val position = launches.indexOfLast { (it as Launches).dateLocal > Calendar.getInstance().time }
            if (position >= 0) {
                scrollTrigger.tryEmit(Pair(position, launches[position] as Launches))
                scrollTrigger.resetReplayCache()
            }
        }

        with(view.parent as View) {
            postDelayed({ visibility = View.GONE }, 1000)
        }
    }
}