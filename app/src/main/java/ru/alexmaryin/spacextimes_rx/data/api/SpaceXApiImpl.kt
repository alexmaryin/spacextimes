package ru.alexmaryin.spacextimes_rx.data.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.lists.*
import java.io.File
import javax.inject.Inject

class SpaceXApiImpl @Inject constructor(private val apiService: RetrofitApiService) : SpaceXApi {

    private val populateNestedLaunches = ApiOptions(
        populate = listOf(
            PopulatedObject(path = "launches", populate = PopulatedObject(path = "rocket")),
            PopulatedObject(path = "rockets")
        )
    )

    private val dragonWithCapsule = PopulatedObject(path = "dragon", populate = PopulatedObject(path = "capsule"))

    private val populateLaunchDetails = ApiOptions(
        populate = listOf(
            PopulatedObject(path = "rocket crew capsules payloads launchpad"),
            PopulatedObject(path = "cores", populate = PopulatedObject(path = "core")),
            PopulatedObject(path = "payloads", populate = dragonWithCapsule)
        )
    )

    private val populatePayload = ApiOptions(populate = listOf(dragonWithCapsule))

    private val populateLaunches = ApiOptions(
        populate = listOf(PopulatedObject(path = "rocket")),
        sort = "field  -upcoming -date_local -name",
        pagination = false,
    )

    private fun requestById(id: String, options: ApiOptions = ApiOptions()) = ApiRequest(ApiQuery(id), options)

//    private fun requestById(id: String, options: Map<String, Any> = emptyMap()) = Gson().toJson(
//        ApiRequest(query = mapOf("_id" to id), options = options)
//    ).toRequestBody("application/json".toMediaTypeOrNull())

    override suspend fun getCapsules(): Response<List<Capsules>> = apiService.getCapsules()
    override suspend fun getCapsuleById(id: String): Response<ApiResponse<Capsule>> =
        apiService.getCapsuleById(requestById(id, populateNestedLaunches))

    override suspend fun getCores(): Response<List<Cores>> = apiService.getCores()
    override suspend fun getCoreById(id: String): Response<ApiResponse<Core>> =
        apiService.getCoreById(requestById(id, populateNestedLaunches))

    override suspend fun getCrew(): Response<List<Crews>> = apiService.getCrew()
    override suspend fun getCrewById(id: String): Response<ApiResponse<Crew>> =
        apiService.getCrewById(requestById(id, populateNestedLaunches))

    override suspend fun getDragons(): Response<List<Dragon>> = apiService.getDragons()
    override suspend fun getDragonById(id: String): Response<Dragon> = apiService.getDragonById(id)

    override suspend fun getLaunchPads(): Response<List<LaunchPads>> = apiService.getLaunchPads()
    override suspend fun getLaunchPadById(id: String): Response<ApiResponse<LaunchPad>> =
        apiService.getLaunchPadById(requestById(id, populateNestedLaunches))

    override suspend fun getLandingPads(): Response<List<LandingPads>> = apiService.getLandingPads()
    override suspend fun getLandingPadById(id: String): Response<ApiResponse<LandingPad>> =
        apiService.getLandingPadById(requestById(id, populateNestedLaunches))

    override suspend fun getRockets(): Response<List<Rocket>> = apiService.getRockets()
    override suspend fun getRocketById(id: String): Response<Rocket> = apiService.getRocketById(id)

    override suspend fun getLaunches(): Response<ApiResponse<Launches>> =
        apiService.getLaunches(ApiRequest(options = populateLaunches))

    override suspend fun getLaunchById(id: String): Response<ApiResponse<Launch>> =
        apiService.getLaunchById(requestById(id, populateLaunchDetails))

    override suspend fun getPayloadById(id: String): Response<ApiResponse<Payload>> =
        apiService.getPayloadById(requestById(id, populatePayload))

    override suspend fun getHistoryEvents(): Response<List<History>> = apiService.getHistoryEvents()

    override suspend fun translate(file: File): Response<PlainTextResponse> {
        val lang = "en-ru".toRequestBody("application/json".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.path, file.asRequestBody("text/plain".toMediaTypeOrNull()))
        return apiService.translate(lang, body)
    }
}