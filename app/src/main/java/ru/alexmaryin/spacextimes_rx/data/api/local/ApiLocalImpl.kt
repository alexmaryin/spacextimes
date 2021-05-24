package ru.alexmaryin.spacextimes_rx.data.api.local

import ru.alexmaryin.spacextimes_rx.data.api.local.spacex.SpaceXDao
import ru.alexmaryin.spacextimes_rx.data.model.*
import javax.inject.Inject

class ApiLocalImpl @Inject constructor(
    private val spaceXDao: SpaceXDao,
): ApiLocal {

    override suspend fun getCapsules(): List<Capsule> = spaceXDao.selectAllCapsules().map { it.toResponse() }

    override suspend fun saveCapsules(capsules: List<Capsule>) = spaceXDao.insertCapsules(capsules.map { it.toRoom() })

    override suspend fun saveCapsuleDetails(capsule: Capsule) = spaceXDao.insertCapsuleWithLaunches(capsule)

    override suspend fun getCapsuleById(id: String): Capsule? = spaceXDao.selectCapsule(id)?.toResponse()

    override suspend fun getCores(): List<Core> = spaceXDao.selectAllCores().map { it.toResponse() }

    override suspend fun getCoreById(id: String): Core? = spaceXDao.selectCore(id)?.toResponse()

    override suspend fun saveCores(cores: List<Core>) = spaceXDao.insertCores(cores.map { it.toRoom() })

    override suspend fun saveCoreDetails(core: Core) = spaceXDao.insertCoreWithLaunches(core)

    override suspend fun getCrew(): List<Crew> = spaceXDao.selectAllCrew().map { it.toResponse() }

    override suspend fun getCrewById(id: String): Crew? = spaceXDao.selectCrewMember(id)?.toResponse()

    override suspend fun saveCrew(crew: List<Crew>) = spaceXDao.insertCrew(crew.map { it.toRoom() })

    override suspend fun saveCrewDetails(crew: Crew) = spaceXDao.insertCrewWithLaunches(crew)

    override suspend fun getDragons(): List<Dragon> = spaceXDao.selectAllDragons().map { it.toResponse() }

    override suspend fun getDragonById(id: String): Dragon? = spaceXDao.selectDragon(id)?.toResponse()

    override suspend fun saveDragons(dragons: List<Dragon>) = spaceXDao.insertDragons(dragons.map { it.toRoom() })

    override suspend fun getLaunchPads(): List<LaunchPad> = spaceXDao.selectAllLaunchPads().map { it.toResponse() }

    override suspend fun getLaunchPadById(id: String): LaunchPad? = spaceXDao.selectLaunchPad(id)?.toResponse()

    override suspend fun saveLaunchPads(pads: List<LaunchPad>) = spaceXDao.insertLaunchPads(pads.map { it.toRoom() })

    override suspend fun getLandingPads(): List<LandingPad> = spaceXDao.selectAllLandingPads().map { it.toResponse() }

    override suspend fun getLandingPadById(id: String): LandingPad? = spaceXDao.selectLandingPad(id)?.toResponse()

    override suspend fun saveLandingPads(pads: List<LandingPad>) = spaceXDao.insertLandingPads(pads.map { it.toRoom() })

    override suspend fun getRockets(): List<Rocket> = spaceXDao.selectAllRockets().map { it.toResponse() }

    override suspend fun getRocketById(id: String): Rocket? = spaceXDao.selectRocket(id)?.toResponse()

    override suspend fun saveRockets(rockets: List<Rocket>) = spaceXDao.insertRockets(rockets.map { it.toRoom() })

    override suspend fun getLaunches(): List<Launch> = spaceXDao.selectAllLaunches().map { it.toResponse() }

    override suspend fun getLaunchById(id: String): Launch? = spaceXDao.selectLaunch(id)?.toResponse { coreId ->
        spaceXDao.selectCoreFlight(coreId)?.toResponse()
    }

    override suspend fun saveLaunches(launches: List<Launch>) = spaceXDao.insertLaunches(launches.map { it.toRoom().launch })

    override suspend fun saveLaunchDetails(launch: Launch) = spaceXDao.insertLaunchWithDetails(launch)

    override suspend fun getPayloadById(id: String): Payload? = spaceXDao.selectPayload(id)?.toResponse()

    override suspend fun savePayload(payload: Payload) = spaceXDao.insertPayloads(listOf(payload.toRoom().payload))

    override suspend fun getHistoryEvents(): List<History> = spaceXDao.selectAllHistoryEvents().map { it.toResponse() }

    override suspend fun saveHistoryEvents(events: List<History>) = spaceXDao.insertHistoryEvents(events.map { it.toRoom() })
}