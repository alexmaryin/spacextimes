package ru.alexmaryin.spacextimes_rx.data.api

import retrofit2.Response
import retrofit2.http.GET
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

    @GET(SpacexUrls.AllDragons)
    suspend fun getDragons(): Response<List<Dragon>>
}