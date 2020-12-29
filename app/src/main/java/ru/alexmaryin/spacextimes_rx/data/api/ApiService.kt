package ru.alexmaryin.spacextimes_rx.data.api

import retrofit2.http.GET
import ru.alexmaryin.spacextimes_rx.data.api.SpacexUrls.Companion.AllCapsules
import ru.alexmaryin.spacextimes_rx.data.model.Capsule

interface ApiService {

    @GET(AllCapsules)
    suspend fun getCapsules(): List<Capsule>

    @GET(AllCores)
    suspend fun getCores(): List<Core>

    @GET(AllCrew)
    suspend fun getCrew(): List<Crew>

    @GET(AllDragons)
    suspend fun getDragons(): List<Dragon>
}