package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.emptyClickListener
import ru.alexmaryin.spacextimes_rx.ui.view.fragments.MainFragmentDirections
import ru.alexmaryin.spacextimes_rx.utils.Result

enum class LaunchFilter {
    Upcoming,
    Past,
    Successfully,
    Failed
}

sealed class MainScreen {
    abstract val name: String
    abstract val titleRes: Int
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
        repository.getCapsules(listOf(translator::translateLastUpdate))
}

object Cores : MainScreen() {
    override val name = "Cores"
    override val titleRes = R.string.coresTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowCoreDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        repository.getCores(listOf(translator::translateLastUpdate))
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
        repository.getDragons(listOf(translator::translateDescription))
}

object Rockets : MainScreen() {
    override val name = "Rockets"
    override val titleRes: Int = R.string.rocketsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowRocketDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        repository.getRockets(listOf(translator::translateDescription))
}

object LaunchesScr : MainScreen() {
    override val name = "Launches"
    override val titleRes= R.string.launchesTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowLaunchDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        repository.getLaunches(listOf(translator::translateDetails))
}

object LaunchPads : MainScreen() {
    override val name = "Launch pads"
    override val titleRes = R.string.launchPadsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowLaunchPadDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        repository.getLaunchPads(listOf(translator::translateDetails))
}

object LandingPads : MainScreen() {
    override val name = "landing pads"
    override val titleRes = R.string.landingPadsTitle

    override fun setClickListener(navController: NavController) = AdapterClickListenerById { id, _ ->
        navController.navigate(MainFragmentDirections.actionShowLandingPadDetails(id))
    }

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        repository.getLandingPads(listOf(translator::translateDetails))
}

object HistoryEvents : MainScreen() {
    override val name = "History events"
    override val titleRes = R.string.historyEventsTitle

    override fun setClickListener(navController: NavController) = emptyClickListener

    override fun readRepository(repository: SpacexDataRepository, translator: TranslatorApi) =
        repository.getHistoryEvents(listOf(translator::translateDetails, translator::translateTitle))
}

