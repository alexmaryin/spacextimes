package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import android.util.Log
import androidx.room.Dao
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.*

@Dao
abstract class SpaceXFlightsDao : CapsulesDao, CoresDao, CrewDao, LandingPadsDao, LaunchPadDao, LaunchDao,
    HistoryDao, RocketDao, DragonDao, PayloadDao, CoreFlightsDao, CrewFlightsDao, JunctionsDao {

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

    suspend fun insertCrewWithLaunches(crew: Crew) {
        insertMember(crew.toRoom())
        insertLaunchesToCrew(crew.launches.map { LaunchesToCrew(it.id, crew.id) })
    }

    suspend fun insertCrewWithLaunches(crewList: List<Crew>) {
        insertCrew(crewList.map { crew ->
            insertLaunchesToCrew(crew.launches.map { LaunchesToCrew(it.id, crew.id) })
            crew.toRoom()
        })
    }

    private suspend fun insertLaunchDetails(launch: Launch) {
        with(launch) {
            rocket?.let { insertRocket(it.toRoom()) }
            launchPad?.let { insertLaunchPad(it.toRoom()) }
            insertCapsules(capsules.map { it.toRoom() })
            insertLaunchesToCapsule(capsules.map { LaunchesToCapsules(id, it.id) })
            insertPayloads(payloads.map { it.toRoom().payload })
            insertLaunchesToPayloads(payloads.map { LaunchesToPayloads(id, it.id) })
            insertCrewFlights(crewFlight.map {
                insertMember(it.member.toRoom())
                insertLaunchesToCrew(LaunchesToCrew(id, it.member.id))
                it.toRoom(id).crewFlight
            })
            insertCoreFlights(cores.filter { it.isNotEmpty }.map {
                insertCore(it.core!!.toRoom())
                insertLaunchesToCore(LaunchesToCores(id, it.core!!.id))
                it.toRoom(id)!!.coreFlight
            })
        }
    }

    suspend fun insertLaunchesWithDetails(launches: List<Launch>) {
        try {
            insertLaunches(launches.map { launch ->
                insertLaunchDetails(launch)
                launch.toRoom().launch
            })
        } catch (e: Exception) {
            Log.e("INSERT_LAUNCH", e.stackTraceToString())
            throw (e)
        }

    }

    suspend fun insertLaunchWithDetails(launch: Launch) {
        insertLaunchDetails(launch)
        insertLaunch(launch.toRoom().launch)
    }

    suspend fun insertLandingPadsWithLaunches(landingPads: List<LandingPad>) {
        insertLandingPads(landingPads.map { pad ->
            insertLaunchesToLandingPads(pad.launches.map { LaunchesToLandingPads(it.id, pad.id) })
            pad.toRoom()
        })
    }

    suspend fun clearJunctions() {
        clearLaunchesToCapsules()
        clearLaunchesToCores()
        clearLaunchesToCrew()
        clearLaunchesToLandingPads()
        clearLaunchesToPayloads()
    }

    suspend fun clearJunctionsForLaunch(launch: Launch) {
        clearLaunchToCapsules(launch.id)
        clearLaunchToCrew(launch.id)
        clearLaunchToPayloads(launch.id)
        clearLaunchToCores(launch.id)
    }

    suspend fun clearDatabase() {
        clearJunctions()
        clearCapsules()
        clearCoreFlights()
        clearCores()
        clearCrewFlights()
        clearCrew()
        clearDragons()
        clearHistoryEvents()
        clearLandingPads()
        clearLaunches()
        clearLaunchPads()
        clearPayloads()
        clearRockets()
    }
}