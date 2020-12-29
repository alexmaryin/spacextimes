package ru.alexmaryin.spacextimes_rx.data.api

import retrofit2.http.GET
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.Dragon

interface ApiService {

    @GET(SpacexUrls.AllCapsules)
    suspend fun getCapsules(): List<Capsule>

    @GET(SpacexUrls.AllCores)
    suspend fun getCores(): List<Core>

    @GET(SpacexUrls.AllCrew)
    suspend fun getCrew(): List<Crew>

    @GET(SpacexUrls.AllDragons)
    suspend fun getDragons(): List<Dragon>
}