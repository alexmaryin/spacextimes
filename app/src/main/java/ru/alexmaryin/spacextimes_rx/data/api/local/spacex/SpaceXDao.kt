package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import android.util.Log
import androidx.room.Dao
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.*

@Dao
abstract class SpaceXDao : CapsulesDao, CoresDao, CrewDao, LandingPadsDao, LaunchPadDao, LaunchDao,
    HistoryDao, RocketDao, DragonDao, PayloadDao, CoreFlightsDao, LaunchCrewDao, JunctionsDao {

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
            Log.d("INSERT_LAUNCH", "Starting insert launch No. $id/$name")
            rocket?.let { insertRocket(it.toRoom()); Log.d("INSERT_LAUNCH", "-- insert rocket ${it.name}") }
            launchPad?.let { insertLaunchPad(it.toRoom()); Log.d("INSERT_LAUNCH", "-- insert launch pad ${it.name}") }
            insertLaunchCrew(crew.map { Log.d("INSERT_LAUNCH", "-- insert launch crew member ${it.member.name}"); it.toRoom().launchCrew })
            insertCrew(crew.map { Log.d("INSERT_LAUNCH", "-- insert crew member ${it.member.name}"); it.member.toRoom() })
            insertCapsules(capsules.map { it.toRoom() })
            insertPayloads(payloads.map { it.toRoom().payload })
            insertLaunchesToCrew(crew.map { LaunchesToCrew(id, it.member.id) })
            insertLaunchesToCapsule(capsules.map { LaunchesToCapsules(id, it.id) })
            insertLaunchesToPayloads(payloads.map { LaunchesToPayloads(id, it.id) })
            val coreFlightsIds = insertCoreFlights(cores.filter { it.isNotEmpty }.map { it.toRoom()!!.coreFlight })
            Log.d("INSERT_LAUNCH", "-- core Flights inserted with new ids: $coreFlightsIds")
            cores.mapNotNull { it.core }.apply {
                insertCores(map { it.toRoom() })
                insertLaunchesToCore(map { LaunchesToCores(id, it.id) })
                insertLaunchesToCoreFlight(zip(coreFlightsIds) { core, flightId ->
                    LaunchesToCoreFlights(core.id, flightId.toInt()) })
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

    suspend fun insertLandingPadsWithLaunches(landingPads: List<LandingPad>) {
        insertLandingPads(landingPads.map { pad ->
            insertLaunchesToLandingPads(pad.launches.map { LaunchesToLandingPads(it.id, pad.id) })
            pad.toRoom()
        })
    }
}