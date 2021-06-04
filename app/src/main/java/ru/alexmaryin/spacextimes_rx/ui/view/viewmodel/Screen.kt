package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.emptyClickListener
import ru.alexmaryin.spacextimes_rx.ui.view.filters.*
import ru.alexmaryin.spacextimes_rx.ui.view.fragments.MainFragmentDirections
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.prettifyMillisecondsPeriod
import java.util.*
import kotlin.time.ExperimentalTime

sealed class MainScreen {
    abstract val name: String
    abstract val titleRes: Int
    open val filter: ListFilter = EmptyFilter
    open val searchable = false
    abstract fun setClickListener(navController: NavController): AdapterClickListenerById
    abstract fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi): Flow<Result>
    open fun <T> getPositionToScroll(context: Context, items: List<T>): Pair<Int, String>? = null
    fun <T> getFilteredList(items: List<T>) = items.filter(filter::predicate)
}

object Capsules : MainScreen() {
    override val name = "Capsules"
    override val titleRes = R.string.capsulesTitle
    override val filter = CapsuleFilter

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowCapsuleDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getCapsules(name).translateLastUpdate() }

}

object Cores : MainScreen() {
    override val name = "Cores"
    override val titleRes = R.string.coresTitle
    override val filter = CoreFilter

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowCoreDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getCores(name).translateLastUpdate() }
}

object Crew : MainScreen() {
    override val name = "Crew"
    override val titleRes = R.string.crewTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowCrewMember(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        repository.getCrew(name)
}

object Dragons : MainScreen() {
    override val name = "Dragons"
    override val titleRes: Int = R.string.dragonsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowDragonDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getDragons(name).translateDescription() }
}

object Rockets : MainScreen() {
    override val name = "Rockets"
    override val titleRes: Int = R.string.rocketsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowRocketDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getRockets(name).translateDescription() }
}

object Launches : MainScreen() {
    override val name = "Launches"
    override val titleRes = R.string.launchesTitle
    override val filter = LaunchFilter
    override val searchable = true

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowLaunchDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getLaunches(name).translateDetails() }

    @ExperimentalTime
    override fun <T> getPositionToScroll(context: Context, items: List<T>) =
        with(items.map { it as Launch }) {
            val position = indexOfLast { it.datePrecision >= DatePrecision.DAY && it.dateLocal > Calendar.getInstance().time }
            val timeTo = (get(position).dateLocal.time - Calendar.getInstance().time.time).prettifyMillisecondsPeriod(context)
            val snackText = context.getString(R.string.next_flight_announce, get(position).name, timeTo)
            if (position >= 0) position to snackText else null
        }
}

object LaunchPads : MainScreen() {
    override val name = "Launch pads"
    override val titleRes = R.string.launchPadsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowLaunchPadDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getLaunchPads(name).translateDetails() }
}

object LandingPads : MainScreen() {
    override val name = "landing pads"
    override val titleRes = R.string.landingPadsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowLandingPadDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getLandingPads(name).translateDetails() }
}

object HistoryEvents : MainScreen() {
    override val name = "History events"
    override val titleRes = R.string.historyEventsTitle

    override fun setClickListener(navController: NavController) = emptyClickListener

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getHistoryEvents(name).translateDetails().translateTitle() }
}

