package ru.alexmaryin.spacextimes_rx.data.api.local

import android.util.Log
import ru.alexmaryin.spacextimes_rx.data.api.local.spacex.SpaceXDao
import ru.alexmaryin.spacextimes_rx.data.model.*
import javax.inject.Inject

class ApiLocalImpl @Inject constructor(
    private val spaceXDao: SpaceXDao,
): ApiLocal {

    override suspend fun getCapsules(): List<Capsule> = spaceXDao.selectAllCapsules().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all capsules from Room: ${it.size} items")
        }

    override suspend fun saveCapsules(capsules: List<Capsule>) {
        capsules.forEach { spaceXDao.insertCapsule(it.toRoom()) }
        // TODO maybe save details of launches?
        Log.d("REPO_LOCAL", "Saving capsules to Room: ${capsules.size} items")
    }

    override suspend fun getCapsuleById(id: String): Capsule? =
        spaceXDao.selectCapsule(id)?.toResponse().apply {
            // TODO launches = spaceXDao.selectLaunchesForCapsule(id) <- this is Capsule id for query
        }
            .also {
                it?.let { Log.d("REPO_LOCAL", "Selected capsule from Room with id: ${it.id}") }
            }

    override suspend fun getCores(): List<Core> = spaceXDao.selectAllCores().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all cores from Room: ${it.size} items")
        }

    override suspend fun getCoreById(id: String): Core? = spaceXDao.selectCore(id)?.toResponse().apply {
        // TODO launches = spaceXDao.selectLaunchesForCore(id) <- this is Core id for query
    }
        .also {
            it?.let { Log.d("REPO_LOCAL", "Selected core from Room with id: ${it.id}") }
        }

    override suspend fun saveCores(cores: List<Core>) {
        cores.forEach { spaceXDao.insertCore(it.toRoom()) }
        // TODO maybe save details of launches?
        Log.d("REPO_LOCAL", "Saving cores to Room: ${cores.size} items")
    }

    override suspend fun getCrew(): List<Crew> = spaceXDao.selectAllCrew().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all crew members from Room: ${it.size} items")
        }

    override suspend fun getCrewById(id: String): Crew? = spaceXDao.selectCrewMember(id)?.toResponse().apply {
        // TODO launches = spaceXDao.selectLaunchesForCrew(id) <- this is Crew id for query
    }
        .also {
            it?.let { Log.d("REPO_LOCAL", "Selected crew member from Room with id: ${it.id}") }
        }

    override suspend fun saveCrew(crew: List<Crew>) {
        crew.forEach { spaceXDao.insertCrew(it.toRoom()) }
        // TODO maybe save details of launches?
        Log.d("REPO_LOCAL", "Saving crew members to Room: ${crew.size} items")
    }

    override suspend fun getDragons(): List<Dragon> = spaceXDao.selectAllDragons().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all dragons from Room: ${it.size} items")
        }

    override suspend fun getDragonById(id: String): Dragon? = spaceXDao.selectDragon(id)?.toResponse().also {
        it?.let { Log.d("REPO_LOCAL", "Selected dragon from Room with id: ${it.id}") }
    }

    override suspend fun saveDragons(dragons: List<Dragon>) {
        dragons.forEach { spaceXDao.insertDragon(it.toRoom()) }
        Log.d("REPO_LOCAL", "Saving dragons to Room: ${dragons.size} items")
    }

    override suspend fun getLaunchPads(): List<LaunchPad> = spaceXDao.selectAllLaunchPads().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all launch pads from Room: ${it.size} items")
        }

    override suspend fun getLaunchPadById(id: String): LaunchPad? = spaceXDao.selectLaunchPad(id)?.toResponse().apply {
        // TODO launches = spaceXDao.selectLaunchesForLaunchPad(id) <- this is Launch pad id for query
    }
        .also {
            it?.let { Log.d("REPO_LOCAL", "Selected launch pad from Room with id: ${it.id}") }
        }

    override suspend fun saveLaunchPads(pads: List<LaunchPad>) {
        pads.forEach { spaceXDao.insertLaunchPad(it.toRoom()) }
        // TODO maybe save details of launches?
        Log.d("REPO_LOCAL", "Saving launch pads to Room: ${pads.size} items")
    }

    override suspend fun getLandingPads(): List<LandingPad> = spaceXDao.selectAllLandingPads().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all landing pads from Room: ${it.size} items")
        }

    override suspend fun getLandingPadById(id: String): LandingPad? = spaceXDao.selectLandingPad(id)?.toResponse().apply {
        // TODO launches = spaceXDao.selectLaunchesForLandingPad(id) <- this is Landing pad id for query
    }
        .also {
            it?.let { Log.d("REPO_LOCAL", "Selected landing pad from Room with id: ${it.id}") }
        }

    override suspend fun saveLandingPads(pads: List<LandingPad>) {
        pads.forEach { spaceXDao.insertLandingPad(it.toRoom()) }
        // TODO maybe save details of launches?
        Log.d("REPO_LOCAL", "Saving landing pads to Room: ${pads.size} items")
    }

    override suspend fun getRockets(): List<Rocket> = spaceXDao.selectAllRockets().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all rockets from Room: ${it.size} items")
        }

    override suspend fun getRocketById(id: String): Rocket? = spaceXDao.selectRocket(id)?.toResponse().also {
        it?.let { Log.d("REPO_LOCAL", "Selected rocket from Room with id: ${it.id}") }
    }

    override suspend fun saveRockets(rockets: List<Rocket>) {
        rockets.forEach { spaceXDao.insertRocket(it.toRoom()) }
        Log.d("REPO_LOCAL", "Saving rockets to Room: ${rockets.size} items")
    }

    override suspend fun getLaunches(): List<Launch> = spaceXDao.selectAllLaunches().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all launches from Room: ${it.size} items")
        }

    override suspend fun getLaunchById(id: String): Launch? = spaceXDao.selectLaunch(id)?.toResponse().apply {
        // TODO populate lists and objects
    }
        .also {
            it?.let { Log.d("REPO_LOCAL", "Selected launch from Room with id: ${it.id}") }
        }

    override suspend fun saveLaunches(launches: List<Launch>) {
        launches.forEach { spaceXDao.insertLaunch(it.toRoom()) }
        // TODO maybe save details of lists?
        Log.d("REPO_LOCAL", "Saving launches to Room: ${launches.size} items")
    }

    override suspend fun getPayloadById(id: String): Payload? {
        return null
    }

    override suspend fun getHistoryEvents(): List<History> = spaceXDao.selectAllHistoryEvents().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all history events from Room: ${it.size} items")
        }

    override suspend fun saveHistoryEvents(events: List<History>) {
        events.forEach { spaceXDao.insertHistoryEvent(it.toRoom()) }
        Log.d("REPO_LOCAL", "Saving history events to Room: ${events.size} items")
    }
}