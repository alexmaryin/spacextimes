package ru.alexmaryin.spacextimes_rx.data.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.model.*
import javax.inject.Inject

class SpaceXApiImpl @Inject constructor(private val apiService: ApiService): SpaceXApi {

    override suspend fun getCapsules(): Response<List<Capsule>> = apiService.getCapsules()
    override suspend fun getCapsuleById(id: String): Response<Capsule> = apiService.getCapsuleById(id)

    override suspend fun getCores(): Response<List<Core>> = apiService.getCores()
    override suspend fun getCoreById(id: String): Response<Core> = apiService.getCoreById(id)

    override suspend fun getCrew(): Response<List<Crew>> = apiService.getCrew()
    override suspend fun getCrewById(id: String): Response<Crew> = apiService.getCrewById(id)

    override suspend fun getDragons(): Response<List<Dragon>> = apiService.getDragons()
    override suspend fun getDragonById(id: String): Response<Dragon> = apiService.getDragonById(id)

    override suspend fun getLaunchPads(): Response<List<LaunchPad>> = apiService.getLaunchPads()
    override suspend fun getLaunchPadById(id: String): Response<LaunchPad> = apiService.getLaunchPadById(id)

    override suspend fun getLandingPads(): Response<List<LandingPad>> =apiService.getLandingPads()
    override suspend fun getLandingPadById(id: String): Response<LandingPad> = apiService.getLandingPadById(id)

    override suspend fun getRockets(): Response<List<Rocket>> = apiService.getRockets()
    override suspend fun getRocketById(id: String): Response<Rocket> = apiService.getRocketById(id)

    override suspend fun getLaunches(): Response<List<Launch>> = apiService.getLaunches()
    override suspend fun getLaunchById(id: String): Response<Launch> = apiService.getLaunchById(id)

    override suspend fun translate(source: String): Response<PlainTextResponse> {
        val body = JSONObject().run {
            put("source", source)
            put("lang", "en-ru")
            put("as", "json")
        }.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.translate(body)
    }
}