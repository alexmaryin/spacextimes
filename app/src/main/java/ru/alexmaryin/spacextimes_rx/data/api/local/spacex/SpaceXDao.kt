package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Dao
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.*

@Dao
abstract class SpaceXDao : CapsulesDao, CoresDao, CrewDao, LandingPadsDao, LaunchPadDao, LaunchDao,
    HistoryDao, RocketDao, DragonDao, PayloadDao, CoreFlightsDao, JunctionsDao {

    suspend fun insertCapsuleWithLaunches(capsule: Capsule) {
        insertCapsules(listOf(capsule.toRoom()))
        if (capsule.launches.isNotEmpty()) {
            insertLaunches(capsule.launches.map {
                insertLaunchesToCapsule(LaunchesToCapsules(it.id, capsule.id))
                it.toRoom().launch
            })
        }
    }

    suspend fun insertCoreWithLaunches(core: Core) {
        insertCores(listOf(core.toRoom()))
        if (core.launches.isNotEmpty()) {
            insertLaunches(core.launches.map {
                insertLaunchesToCore(LaunchesToCores(it.id, core.id))
                it.toRoom().launch
            })
        }
    }

    suspend fun insertCrewWithLaunches(crew: Crew) {
        insertCrew(listOf(crew.toRoom()))
        if (crew.launches.isNotEmpty()) {
            insertLaunches(crew.launches.map {
                insertLaunchesToCrew(LaunchesToCrew(it.id, crew.id))
                it.toRoom().launch
            })
        }
    }

    suspend fun insertLaunchWithDetails(launch: Launch) {
        insertLaunches(listOf(launch.toRoom().launch))
        with(launch) {
            rocket?.let { insertRockets(listOf(it.toRoom())) }
            launchPad?.let { insertLaunchPads(listOf(it.toRoom())) }
            crew.forEach { insertLaunchesToCrew(LaunchesToCrew(id, it.id)) }
            insertCrew(crew.map { it.toRoom() })
            capsules.forEach { insertLaunchesToCapsule(LaunchesToCapsules(id, it.id)) }
            insertCapsules(capsules.map { it.toRoom() })
            cores.forEach { insertLaunchesToCoreFlight(LaunchesToCoreFlights(id, it.core.id)) }
            insertCoreFlights(cores.map { it.toRoom().coreFlight })
            payloads.forEach { insertLaunchesToPayloads(LaunchesToPayloads(id, it.id)) }
            insertPayloads(payloads.map { it.toRoom().payload })
        }
    }
}