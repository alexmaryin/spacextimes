package ru.alexmaryin.spacextimes_rx.data.api.remote

import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.api.*
import javax.inject.Inject

class SpaceXApiImpl @Inject constructor(private val apiRemote: ApiRemote) : SpaceXApi {

    private val populateNestedLaunches = ApiOptions(
        populate = listOf(
            PopulatedObject(
                select = "-crew -capsules -cores -payloads -launchpad",
                path = "launches",
                populate = PopulatedObject(path = "rocket")),
            PopulatedObject(path = "rockets")
        ),
    )

    private val dragonWithCapsule = PopulatedObject(path = "dragon", populate = PopulatedObject(path = "capsule", select = "-launches"))

    private val populateLaunchDetails = ApiOptions(
        populate = listOf(
            PopulatedObject(path = "rocket"),
            PopulatedObject(path = "launchpad", select = "-launches -rockets"),
            PopulatedObject(path = "crew", populate = PopulatedObject(path = "crew", select = "-launches")),
            PopulatedObject(path = "capsules", select = "-launches"),
            PopulatedObject(path = "cores", populate = PopulatedObject(path = "core", select = "-launches")),
            PopulatedObject(path = "payloads", populate = dragonWithCapsule)
        ),
        sort = "field -upcoming -flight_number name",
        pagination = false
    )

    private val populatePayload = ApiOptions(populate = listOf(dragonWithCapsule))

    private fun requestById(id: String, options: ApiOptions = ApiOptions()) = ApiRequest(ApiQuery(id), options)

    override suspend fun getCapsules(): Response<ApiResponse<Capsule>> =
        apiRemote.getCapsules(ApiRequest(options = populateNestedLaunches.apply { sort = "field -serial" }))

    override suspend fun getCapsuleById(id: String): Response<ApiResponse<Capsule>> =
        apiRemote.getCapsuleById(requestById(id, populateNestedLaunches))

    override suspend fun getCores(): Response<ApiResponse<Core>> =
        apiRemote.getCores(ApiRequest(options = populateNestedLaunches.apply { sort = "field -block -serial" }))

    override suspend fun getCoreById(id: String): Response<ApiResponse<Core>> =
        apiRemote.getCoreById(requestById(id, populateNestedLaunches))

    override suspend fun getCrew(): Response<ApiResponse<Crew>> =
        apiRemote.getCrew(ApiRequest(options = populateNestedLaunches.apply { sort = "field _id" }))

    override suspend fun getCrewById(id: String): Response<ApiResponse<Crew>> =
        apiRemote.getCrewById(requestById(id, populateNestedLaunches))

    override suspend fun getDragons(): Response<ApiResponse<Dragon>> =
        apiRemote.getDragons(ApiRequest(options = ApiOptions(sort = "field name")))

    override suspend fun getDragonById(id: String): Response<ApiResponse<Dragon>> =
        apiRemote.getDragonById(requestById(id))

    override suspend fun getLaunchPads(): Response<ApiResponse<LaunchPad>> =
        apiRemote.getLaunchPads(ApiRequest(options = populateNestedLaunches.apply { sort = "field name" }))

    override suspend fun getLaunchPadById(id: String): Response<ApiResponse<LaunchPad>> =
        apiRemote.getLaunchPadById(requestById(id, populateNestedLaunches))

    override suspend fun getLandingPads(): Response<ApiResponse<LandingPad>> =
        apiRemote.getLandingPads(ApiRequest(options = populateNestedLaunches.apply { sort = "field name" }))

    override suspend fun getLandingPadById(id: String): Response<ApiResponse<LandingPad>> =
        apiRemote.getLandingPadById(requestById(id, populateNestedLaunches))

    override suspend fun getRockets(): Response<ApiResponse<Rocket>> =
        apiRemote.getRockets(ApiRequest(options = ApiOptions(sort = "field name")))

    override suspend fun getRocketById(id: String): Response<ApiResponse<Rocket>> =
        apiRemote.getRocketById(requestById(id))

    override suspend fun getLaunches(): Response<ApiResponse<Launch>> =
        apiRemote.getLaunches(ApiRequest(options = populateLaunchDetails))

    override suspend fun getLaunchById(id: String): Response<ApiResponse<Launch>> =
        apiRemote.getLaunchById(requestById(id, populateLaunchDetails))

    override suspend fun getPayloadById(id: String): Response<ApiResponse<Payload>> =
        apiRemote.getPayloadById(requestById(id, populatePayload))

    override suspend fun getHistoryEvents(): Response<ApiResponse<History>> =
        apiRemote.getHistoryEvents(ApiRequest(options = ApiOptions(sort = "field event_date_unix")))
}