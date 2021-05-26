package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.data.api.filters.filterLaunchesWith
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.emptyClickListener
import ru.alexmaryin.spacextimes_rx.ui.view.fragments.MainFragmentDirections
import ru.alexmaryin.spacextimes_rx.utils.Result

sealed class MainScreen {
    abstract val name: String
    abstract val titleRes: Int
    open val filter: ListFilter = EmptyFilter
    abstract fun setClickListener(navController: NavController): AdapterClickListenerById
    abstract fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi): Flow<Result>
}

object Capsules : MainScreen() {
    override val name = "Capsules"
    override val titleRes = R.string.capsulesTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowCapsuleDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getCapsules().translateLastUpdate() }
}

object Cores : MainScreen() {
    override val name = "Cores"
    override val titleRes = R.string.coresTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowCoreDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getCores().translateLastUpdate() }
}

object Crew : MainScreen() {
    override val name = "Crew"
    override val titleRes = R.string.crewTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowCrewMember(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        repository.getCrew()
}

object Dragons : MainScreen() {
    override val name = "Dragons"
    override val titleRes: Int = R.string.dragonsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowDragonDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getDragons().translateDescription() }
}

object Rockets : MainScreen() {
    override val name = "Rockets"
    override val titleRes: Int = R.string.rocketsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowRocketDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getRockets().translateDescription() }
}

object Launches : MainScreen() {
    override val name = "Launches"
    override val titleRes= R.string.launchesTitle
    override var filter = LaunchFilter

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowLaunchDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getLaunches().translateDetails().filterLaunchesWith(filter.filters) }
}

object LaunchPads : MainScreen() {
    override val name = "Launch pads"
    override val titleRes = R.string.launchPadsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowLaunchPadDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getLaunchPads().translateDetails() }
}

object LandingPads : MainScreen() {
    override val name = "landing pads"
    override val titleRes = R.string.landingPadsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowLandingPadDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getLandingPads().translateDetails() }
}

object HistoryEvents : MainScreen() {
    override val name = "History events"
    override val titleRes = R.string.historyEventsTitle

    override fun setClickListener(navController: NavController) = emptyClickListener

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        translator.run { repository.getHistoryEvents().translateDetails().translateTitle() }
}

