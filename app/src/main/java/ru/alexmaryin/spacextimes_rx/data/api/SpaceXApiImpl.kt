package ru.alexmaryin.spacextimes_rx.data.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.model.*
import javax.inject.Inject

class SpaceXApiImpl @Inject constructor(private val apiService: RetrofitApiService) : SpaceXApi {

    private val populateLaunches = mapOf(
        "populate" to JsonObject().apply {
            addProperty("path", "launches")
            add("populate", JsonObject().apply { addProperty("path", "rocket") })
        },
        "pagination" to false,
    )

    private fun requestById(id: String, options: Map<String, Any> = emptyMap()) = Gson().toJson(
        ApiQuery(query = mapOf("_id" to id), options = options)
    ).toRequestBody("application/json".toMediaTypeOrNull())

    override suspend fun getCapsules(): Response<List<Capsule>> = apiService.getCapsules()
    override suspend fun getCapsuleById(id: String): Response<Capsule> = apiService.getCapsuleById(id)

    override suspend fun getCores(): Response<List<Cores>> = apiService.getCores()
    override suspend fun getCoreById(id: String): Response<ApiResponse<Core>> =
        apiService.getCoreById(requestById(id, populateLaunches))

    override suspend fun getCrew(): Response<List<Crews>> = apiService.getCrew()
    override suspend fun getCrewById(id: String): Response<ApiResponse<Crew>> =
        apiService.getCrewById(requestById(id, populateLaunches))

    override suspend fun getDragons(): Response<List<Dragon>> = apiService.getDragons()
    override suspend fun getDragonById(id: String): Response<Dragon> = apiService.getDragonById(id)

    override suspend fun getLaunchPads(): Response<List<LaunchPad>> = apiService.getLaunchPads()
    override suspend fun getLaunchPadById(id: String): Response<LaunchPad> = apiService.getLaunchPadById(id)

    override suspend fun getLandingPads(): Response<List<LandingPad>> = apiService.getLandingPads()
    override suspend fun getLandingPadById(id: String): Response<LandingPad> = apiService.getLandingPadById(id)

    override suspend fun getRockets(): Response<List<Rocket>> = apiService.getRockets()
    override suspend fun getRocketById(id: String): Response<Rocket> = apiService.getRocketById(id)

    override suspend fun getLaunches(): Response<ApiResponse<Launches>> {
        val body = Gson().toJson(
            ApiQuery(
                options = mapOf(
                    "populate" to "rocket",
                    "pagination" to false,
                    "sort" to "field -date_local",
                )
            )
        ).toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.getLaunches(body)
    }

    override suspend fun getLaunchById(id: String): Response<Launch> = apiService.getLaunchById(id)

    override suspend fun translate(source: String): Response<PlainTextResponse> {
        val body = JsonObject().run {
            addProperty("source", source)
            addProperty("lang", "en-ru")
            addProperty("as", "json")
        }.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.translate(body)
    }
}