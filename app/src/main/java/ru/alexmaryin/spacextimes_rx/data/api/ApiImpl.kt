package ru.alexmaryin.spacextimes_rx.data.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import javax.inject.Inject

class ApiImpl @Inject constructor(private val apiService: ApiService): Api {

    override suspend fun getCapsules(): Response<List<Capsule>> = apiService.getCapsules()

    override suspend fun getCores(): Response<List<Core>> = apiService.getCores()

    override suspend fun getCrew(): Response<List<Crew>> = apiService.getCrew()

    override suspend fun getDragons(): Response<List<Dragon>> = apiService.getDragons()

    override suspend fun translate(source: String): Response<PlainTextResponse> {
        val body = JSONObject().run {
            put("source", source)
            put("lang", "en-ru")
            put("as", "json")
        }.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return apiService.translate(body)
    }
}