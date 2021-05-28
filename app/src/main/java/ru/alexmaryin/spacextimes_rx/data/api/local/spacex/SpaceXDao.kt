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
        insertLaunchesToCapsule(capsule.launches.map { LaunchesToCapsules(it.id, capsule.id) })
    }

    suspend fun insertCapsuleWithLaunches(capsules: List<Capsule>) {
        insertCapsules(capsules.map { capsule ->
            insertLaunchesToCapsule(capsule.launches.map { LaunchesToCapsules(it.id, capsule.id) })
            capsule.toRoom()
        })
    }

    suspend fun insertCoreWithLaunches(core: Core) {
        insertCore(core.toRoom())
        insertLaunchesToCore(core.launches.map { LaunchesToCores(it.id, core.id) })
    }

    suspend fun insertCoresWithLaunches(cores: List<Core>) {
        insertCores(cores.map { core ->
            insertLaunchesToCore(core.launches.map { LaunchesToCores(it.id, core.id) })
            core.toRoom()
        })
    }

    suspend fun insertCrewWithLaunches(member: Crew) {
        insertMember(member.toRoom())
        insertLaunchesToCrew(member.launches.map { LaunchesToCrew(it.id, member.id) })
    }

    suspend fun insertCrewWithLaunches(crew: List<Crew>) {
        insertCrew(crew.map { member ->
            insertLaunchesToCrew(member.launches.map { LaunchesToCrew(it.id, member.id) })
            member.toRoom()
        })
    }

    private suspend fun insertLaunchDetails(launch: Launch) {
        with(launch) {
            rocket?.let { insertRocket(it.toRoom()) }
            launchPad?.let { insertLaunchPad(it.toRoom()) }
            insertCrew(crew.map { it.toRoom() })
            insertCapsules(capsules.map { it.toRoom() })
            insertCoreFlights(cores.mapNotNull { it.toRoom()?.coreFlight })
            insertPayloads(payloads.map { it.toRoom().payload })
            insertLaunchesToCrew(crew.map { LaunchesToCrew(id, it.id) })
            insertLaunchesToCapsule(capsules.map { LaunchesToCapsules(id, it.id) })
            insertLaunchesToPayloads(payloads.map { LaunchesToPayloads(id, it.id) })
            cores.mapNotNull { it.core }.apply {
                insertCores(map { it.toRoom() })
                insertLaunchesToCore(map { LaunchesToCores(id, it.id) })
                insertLaunchesToCoreFlight(map { LaunchesToCoreFlights(id, it.id) })
            }
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