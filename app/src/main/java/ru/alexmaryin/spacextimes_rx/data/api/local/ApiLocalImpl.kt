package ru.alexmaryin.spacextimes_rx.data.api.local

import ru.alexmaryin.spacextimes_rx.data.api.local.spacex.SpaceXFlightsDao
import ru.alexmaryin.spacextimes_rx.data.model.*
import javax.inject.Inject

class ApiLocalImpl @Inject constructor(
    private val spaceXDao: SpaceXFlightsDao,
) : ApiLocal {

    override suspend fun getCapsules(): List<Capsule> = spaceXDao.selectAllCapsules().map { it.toResponse() }

    override suspend fun saveCapsules(capsules: List<Capsule>) = with(spaceXDao) {
        clearCapsules()
        clearLaunchesToCapsules()
        insertCapsuleWithLaunches(capsules)
    }

    override suspend fun saveCapsuleDetails(capsule: Capsule) = spaceXDao.insertCapsuleWithLaunches(capsule)

    override suspend fun getCapsuleById(id: String): Capsule? = spaceXDao.selectCapsule(id)?.toResponse()

    override suspend fun getCores(): List<Core> = spaceXDao.selectAllCores().map { it.toResponse() }

    override suspend fun getCoreById(id: String): Core? = spaceXDao.selectCore(id)?.toResponse()

    override suspend fun saveCores(cores: List<Core>) = with(spaceXDao) {
        clearCores()
        clearLaunchesToCores()
        insertCoresWithLaunches(cores)
    }

    override suspend fun saveCoreDetails(core: Core) = spaceXDao.insertCoreWithLaunches(core)

    override suspend fun getCrew(): List<Crew> = spaceXDao.selectAllCrew().map { it.toResponse() }

    override suspend fun getCrewById(id: String): Crew? = spaceXDao.selectCrewMember(id)?.toResponse()

    override suspend fun saveCrew(crew: List<Crew>) = with(spaceXDao) {
        clearCrew()
        clearLaunchesToCrew()
        insertCrewWithLaunches(crew)
    }

    override suspend fun saveCrewDetails(member: Crew) = spaceXDao.insertCrewWithLaunches(member)

    override suspend fun getDragons(): List<Dragon> = spaceXDao.selectAllDragons().map { it.toResponse() }

    override suspend fun getDragonById(id: String): Dragon? = spaceXDao.selectDragon(id)?.toResponse()

    override suspend fun saveDragons(dragons: List<Dragon>) = with(spaceXDao) {
        clearDragons()
        insertDragons(dragons.map { it.toRoom() })
    }

    override suspend fun getLaunchPads(): List<LaunchPad> = spaceXDao.selectAllLaunchPads().map { it.toResponse() }

    override suspend fun getLaunchPadById(id: String): LaunchPad? = spaceXDao.selectLaunchPad(id)?.toResponse()

    override suspend fun saveLaunchPads(pads: List<LaunchPad>) = with(spaceXDao) {
        clearLaunchPads()
        insertLaunchPads(pads.map { it.toRoom() })
    }

    override suspend fun getLandingPads(): List<LandingPad> = spaceXDao.selectAllLandingPads().map { it.toResponse() }

    override suspend fun getLandingPadById(id: String): LandingPad? = spaceXDao.selectLandingPad(id)?.toResponse()

    override suspend fun saveLandingPads(pads: List<LandingPad>) = with(spaceXDao) {
        clearLandingPads()
        clearLaunchesToLandingPads()
        insertLandingPadsWithLaunches(pads)
    }

    override suspend fun getRockets(): List<Rocket> = spaceXDao.selectAllRockets().map { it.toResponse() }

    override suspend fun getRocketById(id: String): Rocket? = spaceXDao.selectRocket(id)?.toResponse()

    override suspend fun saveRockets(rockets: List<Rocket>) = with(spaceXDao) {
        clearRockets()
        insertRockets(rockets.map { it.toRoom() })
    }

    override suspend fun getLaunches(): List<Launch> = spaceXDao.selectLaunchesForList().map { it.toResponse() }

    override suspend fun getLaunchById(id: String): Launch? = spaceXDao.selectLaunch(id)?.toResponse(
        crewFlightSelect = { crewId, launchId ->
            spaceXDao.selectCrewFlight(crewId, launchId)?.toResponse()
        },
        coreSelect = { coreId, launchId ->
            spaceXDao.selectCoreFlight(coreId, launchId)?.toResponse()
        })

    override suspend fun saveLaunches(launches: List<Launch>) = with(spaceXDao) {
        clearJunctions()
        clearLaunches()
        insertLaunchesWithDetails(launches)
    }

    override suspend fun saveLaunchDetails(launch: Launch) = with(spaceXDao) {
        clearJunctionsForLaunch(launch)
        insertLaunchWithDetails(launch)
    }

    override suspend fun getPayloadById(id: String): Payload? = spaceXDao.selectPayload(id)?.toResponse()

    override suspend fun savePayload(payload: Payload) = spaceXDao.insertPayload(payload.toRoom().payload)

    override suspend fun getHistoryEvents(): List<History> = spaceXDao.selectAllHistoryEvents().map { it.toResponse() }

    override suspend fun saveHistoryEvents(events: List<History>) = with(spaceXDao) {
        clearHistoryEvents()
        insertHistoryEvents(events.map { it.toRoom() })
    }

    override suspend fun dropLocalData() = spaceXDao.clearDatabase()
}
