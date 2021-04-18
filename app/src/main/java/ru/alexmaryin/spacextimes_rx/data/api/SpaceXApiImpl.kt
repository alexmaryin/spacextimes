package ru.alexmaryin.spacextimes_rx.data.api

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
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

    private val populateLaunches = mapOf(
        "populate" to JsonArray().apply {
            add(JsonObject().apply {
                addProperty("path", "launches")
                add("populate", JsonObject().apply { addProperty("path", "rocket") })
            })
            add(JsonObject().apply {
                addProperty("path", "rockets")
            })
        },
        "pagination" to false,
    )

    private val populateLaunchDetails = mapOf(
        "populate" to JsonArray().apply {
            add(JsonObject().apply { addProperty("path", "rocket crew capsules payloads launchpad") })
            add(JsonObject().apply {
                addProperty("path", "cores")
                addProperty("populate", "core")
            })
            add(JsonObject().apply {
                addProperty("path", "payloads")
                add("populate", JsonObject().apply {
                    addProperty("path", "dragon")
                    addProperty("populate", "capsule")
                })
            })
        }
    )

    private val populatePayload = mapOf(
        "populate" to JsonArray().apply {
            add(JsonObject().apply {
                addProperty("path", "dragon")
                add("populate", JsonObject().apply { addProperty("path", "capsule") })
            })
        }
    )

    private fun requestById(id: String, options: Map<String, Any> = emptyMap()) = Gson().toJson(
        ApiQuery(query = mapOf("_id" to id), options = options)
    ).toRequestBody("application/json".toMediaTypeOrNull())

    override suspend fun getCapsules(): Response<List<Capsules>> = apiService.getCapsules()
    override suspend fun getCapsuleById(id: String): Response<ApiResponse<Capsule>> =
        apiService.getCapsuleById(requestById(id, populateLaunches))

    override suspend fun getCores(): Response<List<Cores>> = apiService.getCores()
    override suspend fun getCoreById(id: String): Response<ApiResponse<Core>> =
        apiService.getCoreById(requestById(id, populateLaunches))

    override suspend fun getCrew(): Response<List<Crews>> = apiService.getCrew()
    override suspend fun getCrewById(id: String): Response<ApiResponse<Crew>> =
        apiService.getCrewById(requestById(id, populateLaunches))

    override suspend fun getDragons(): Response<List<Dragon>> = apiService.getDragons()
    override suspend fun getDragonById(id: String): Response<Dragon> = apiService.getDragonById(id)

    override suspend fun getLaunchPads(): Response<List<LaunchPads>> = apiService.getLaunchPads()
    override suspend fun getLaunchPadById(id: String): Response<ApiResponse<LaunchPad>> =
        apiService.getLaunchPadById(requestById(id, populateLaunches))

    override suspend fun getLandingPads(): Response<List<LandingPads>> = apiService.getLandingPads()
    override suspend fun getLandingPadById(id: String): Response<ApiResponse<LandingPad>> =
        apiService.getLandingPadById(requestById(id, populateLaunches))

    override suspend fun getRockets(): Response<List<Rocket>> = apiService.getRockets()
    override suspend fun getRocketById(id: String): Response<Rocket> = apiService.getRocketById(id)

    override suspend fun getLaunches(): Response<ApiResponse<Launches>> {
        val body = Gson().toJson(
            ApiQuery(
                options = mapOf(
                    "populate" to "rocket",
                    "pagination" to false,
                    "sort" to "field  -upcoming -date_local -name",
                )
            )
        ).toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.getLaunches(body)
    }
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