package ru.alexmaryin.spacextimes_rx.data.api

import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.lists.*
import java.io.File

//enum class LaunchesFilter { ALL, UPCOMING, PAST, LATEST, NEXT }

interface SpaceXApi {

    suspend fun getCapsules(): Response<List<Capsules>>
    suspend fun getCapsuleById(id: String): Response<ApiResponse<Capsule>>

    suspend fun getCores(): Response<List<Cores>>
    suspend fun getCoreById(id: String): Response<ApiResponse<Core>>

    suspend fun getCrew(): Response<List<Crews>>
    suspend fun getCrewById(id: String): Response<ApiResponse<Crew>>

    suspend fun getDragons(): Response<List<Dragon>>
    suspend fun getDragonById(id: String): Response<Dragon>

    suspend fun getLaunchPads(): Response<List<LaunchPads>>
    suspend fun getLaunchPadById(id: String): Response<ApiResponse<LaunchPad>>

    suspend fun getLandingPads(): Response<List<LandingPads>>
    suspend fun getLandingPadById(id: String): Response<ApiResponse<LandingPad>>

    suspend fun getRockets(): Response<List<Rocket>>
    suspend fun getRocketById(id: String): Response<Rocket>

    suspend fun getLaunches(): Response<ApiResponse<Launches>>
    suspend fun getLaunchById(id: String): Response<Launch>

    suspend fun getPayloads(): Response<List<Payload>>
    suspend fun getPayloadById(id: String): Response<Payload>

    suspend fun getHistoryEvents(): Response<List<History>>
    suspend fun getEventById(id: String): Response<History>

    suspend fun translate(file: File): Response<PlainTextResponse>
}