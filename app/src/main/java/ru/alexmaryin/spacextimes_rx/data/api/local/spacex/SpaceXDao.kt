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
        insertCapsule(capsule.toRoom())
        if (capsule.launches.isNotEmpty()) {
            insertLaunches(capsule.launches.map {
                insertLaunchesToCapsule(LaunchesToCapsules(it.id, capsule.id))
                it.toRoom().launch
            })
        }
    }

    suspend fun insertCoreWithLaunches(core: Core) {
        insertCore(core.toRoom())
        if (core.launches.isNotEmpty()) {
            insertLaunches(core.launches.map {
                insertLaunchesToCore(LaunchesToCores(it.id, core.id))
                it.toRoom().launch
            })
        }
    }

    suspend fun insertCrewWithLaunches(crew: Crew) {
        insertMember(crew.toRoom())
        if (crew.launches.isNotEmpty()) {
            insertLaunches(crew.launches.map {
                insertLaunchesToCrew(LaunchesToCrew(it.id, crew.id))
                it.toRoom().launch
            })
        }
    }

    private suspend fun insertLaunchDetails(launch: Launch) {
        with(launch) {
            rocket?.let { insertRocket(it.toRoom()) }
            launchPad?.let { insertLaunchPad(it.toRoom()) }
            crew.forEach { insertLaunchesToCrew(LaunchesToCrew(id, it.id)) }
            insertCrew(crew.map { it.toRoom() })
            capsules.forEach { insertLaunchesToCapsule(LaunchesToCapsules(id, it.id)) }
            insertCapsules(capsules.map { it.toRoom() })
            insertCores(cores.mapNotNull { coreFlight ->
                if (coreFlight.isNotEmpty) insertLaunchesToCoreFlight(LaunchesToCoreFlights(id, coreFlight.core!!.id))
                coreFlight.core?.toRoom()
            })
            insertCoreFlights(cores.mapNotNull { it.toRoom()?.coreFlight })
            payloads.forEach { insertLaunchesToPayloads(LaunchesToPayloads(id, it.id)) }
            insertPayloads(payloads.map { it.toRoom().payload })
        }
    }

    suspend fun insertLaunchesWithDetails(launches: List<Launch>) {
        insertLaunches(launches.map { launch ->
            insertLaunchDetails(launch)
            launch.toRoom().launch
        })
    }

    suspend fun insertLaunchWithDetails(launch: Launch) {
        insertLaunch(launch.toRoom().launch)
        insertLaunchDetails(launch)
    }
}