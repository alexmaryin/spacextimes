package ru.alexmaryin.spacextimes_rx.data.api.local

import android.util.Log
import ru.alexmaryin.spacextimes_rx.data.api.local.spacex.SpaceXDao
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.lists.LandingPads
import ru.alexmaryin.spacextimes_rx.data.model.lists.LaunchPads
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches
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

    override suspend fun getDragons(): List<Dragon> {
        return emptyList()
    }

    override suspend fun getDragonById(id: String): Dragon? {
        Log.d("REPO_LOCAL", "Local dragon with id $id is requested. Return null for debug.")
        return null
    }

    override suspend fun getLaunchPads(): List<LaunchPads> {
        return emptyList()
    }

    override suspend fun getLaunchPadById(id: String): LaunchPad? {
        Log.d("REPO_LOCAL", "Local launch pad with id $id is requested. Return null for debug.")
        return null
    }

    override suspend fun getLandingPads(): List<LandingPads> {
        return emptyList()
    }

    override suspend fun getLandingPadById(id: String): LandingPad? {
        Log.d("REPO_LOCAL", "Local landing pad with id $id is requested. Return null for debug.")
        return null
    }

    override suspend fun getRockets(): List<Rocket> {
        return emptyList()
    }

    override suspend fun getRocketById(id: String): Rocket? {
        Log.d("REPO_LOCAL", "Local rocket with id $id is requested. Return null for debug.")
        return null
    }

    override suspend fun getLaunches(): List<Launches> {
        return emptyList()
    }

    override suspend fun getLaunchById(id: String): Launch? {
        Log.d("REPO_LOCAL", "Local launch with id $id is requested. Return null for debug.")
        return null
    }

    override suspend fun getPayloadById(id: String): Payload? {
        Log.d("REPO_LOCAL", "Local payload with id $id is requested. Return null for debug.")
        return null
    }

    override suspend fun getHistoryEvents(): List<History> {
        return emptyList()
    }
}