package ru.alexmaryin.spacextimes_rx.data.api

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorUrls
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.Dragon

interface ApiService {

    @GET(SpacexUrls.AllCapsules)
    suspend fun getCapsules(): Response<List<Capsule>>

    @GET(SpacexUrls.AllCores)
    suspend fun getCores(): Response<List<Core>>

    @GET(SpacexUrls.AllCrew)
    suspend fun getCrew(): Response<List<Crew>>

    @GET(SpacexUrls.AllCrew+"{id}")
    suspend fun getCrewById(@Path("id") id: String): Response<Crew>

    @GET(SpacexUrls.AllDragons)
    suspend fun getDragons(): Response<List<Dragon>>

    @POST(TranslatorUrls.PlainText)
    suspend fun translate(@Body body: RequestBody): Response<PlainTextResponse>
}