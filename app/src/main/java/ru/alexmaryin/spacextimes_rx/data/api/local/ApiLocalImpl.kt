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
        spaceXDao.insertCapsules(capsules.map { it.toRoom() })
        Log.d("REPO_LOCAL", "Saving capsules to Room: ${capsules.size} items")
    }

    override suspend fun getCapsuleById(id: String): Capsule? =
        spaceXDao.selectCapsule(id)?.toResponse().also {
                it?.let { Log.d("REPO_LOCAL", "Selected capsule from Room with id: ${it.id}") }
            }

    override suspend fun getCores(): List<Core> = spaceXDao.selectAllCores().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all cores from Room: ${it.size} items")
        }

    override suspend fun getCoreById(id: String): Core? = spaceXDao.selectCore(id)?.toResponse().also {
            it?.let { Log.d("REPO_LOCAL", "Selected core from Room with id: ${it.id}") }
        }

    override suspend fun saveCores(cores: List<Core>) {
        spaceXDao.insertCores(cores.map { it.toRoom() })
        Log.d("REPO_LOCAL", "Saving cores to Room: ${cores.size} items")
    }

    override suspend fun getCrew(): List<Crew> = spaceXDao.selectAllCrew().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all crew members from Room: ${it.size} items")
        }

    override suspend fun getCrewById(id: String): Crew? = spaceXDao.selectCrewMember(id)?.toResponse().also {
            it?.let { Log.d("REPO_LOCAL", "Selected crew member from Room with id: ${it.id}") }
        }

    override suspend fun saveCrew(crew: List<Crew>) {
        spaceXDao.insertCrew(crew.map { it.toRoom() })
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
        spaceXDao.insertDragons(dragons.map { it.toRoom() })
        Log.d("REPO_LOCAL", "Saving dragons to Room: ${dragons.size} items")
    }

    override suspend fun getLaunchPads(): List<LaunchPad> = spaceXDao.selectAllLaunchPads().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all launch pads from Room: ${it.size} items")
        }

    override suspend fun getLaunchPadById(id: String): LaunchPad? = spaceXDao.selectLaunchPad(id)?.toResponse().also {
            it?.let { Log.d("REPO_LOCAL", "Selected launch pad from Room with id: ${it.id}") }
        }

    override suspend fun saveLaunchPads(pads: List<LaunchPad>) {
        spaceXDao.insertLaunchPads(pads.map { it.toRoom() })
        Log.d("REPO_LOCAL", "Saving launch pads to Room: ${pads.size} items")
    }

    override suspend fun getLandingPads(): List<LandingPad> = spaceXDao.selectAllLandingPads().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all landing pads from Room: ${it.size} items")
        }

    override suspend fun getLandingPadById(id: String): LandingPad? = spaceXDao.selectLandingPad(id)?.toResponse().also {
            it?.let { Log.d("REPO_LOCAL", "Selected landing pad from Room with id: ${it.id}") }
        }

    override suspend fun saveLandingPads(pads: List<LandingPad>) {
        spaceXDao.insertLandingPads(pads.map { it.toRoom() })
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
        spaceXDao.insertRockets(rockets.map { it.toRoom() })
        Log.d("REPO_LOCAL", "Saving rockets to Room: ${rockets.size} items")
    }

    override suspend fun getLaunches(): List<Launch> = spaceXDao.selectAllLaunches().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all launches from Room: ${it.size} items")
        }

    override suspend fun getLaunchById(id: String): Launch? = spaceXDao.selectLaunch(id)?.toResponse().also {
            it?.let { Log.d("REPO_LOCAL", "Selected launch from Room with id: ${it.id}") }
        }

    override suspend fun saveLaunches(launches: List<Launch>) {
        spaceXDao.insertLaunches(launches.map { it.toRoom() })
        Log.d("REPO_LOCAL", "Saving launches to Room: ${launches.size} items")
    }

    override suspend fun getPayloadById(id: String): Payload? = spaceXDao.selectPayload(id)?.toResponse().also {
        it?.let { Log.d("REPO_LOCAL", "Selected payload from Room with id: ${it.id}") }
    }

    override suspend fun savePayload(payload: Payload) {
        spaceXDao.insertPayload(payload.toRoom())
        Log.d("REPO_LOCAL", "Saving payloads with id:${payload.id} to Room")
    }

    override suspend fun getHistoryEvents(): List<History> = spaceXDao.selectAllHistoryEvents().map { it.toResponse() }
        .also {
            Log.d("REPO_LOCAL", "Selected all history events from Room: ${it.size} items")
        }

    override suspend fun saveHistoryEvents(events: List<History>) {
        spaceXDao.insertHistoryEvents(events.map { it.toRoom() })
        Log.d("REPO_LOCAL", "Saving history events to Room: ${events.size} items")
    }
}