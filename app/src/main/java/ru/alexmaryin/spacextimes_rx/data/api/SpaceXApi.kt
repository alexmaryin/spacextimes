package ru.alexmaryin.spacextimes_rx.data.api

import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.model.*

//enum class LaunchesFilter { ALL, UPCOMING, PAST, LATEST, NEXT }

interface SpaceXApi {

    suspend fun getCapsules(): Response<List<Capsule>>
    suspend fun getCapsuleById(id: String): Response<Capsule>

    suspend fun getCores(): Response<List<Cores>>
    suspend fun getCoreById(id: String): Response<ApiResponse<Core>>

    suspend fun getCrew(): Response<List<Crews>>
    suspend fun getCrewById(id: String): Response<ApiResponse<Crew>>

    suspend fun getDragons(): Response<List<Dragon>>
    suspend fun getDragonById(id: String): Response<Dragon>

    suspend fun getLaunchPads(): Response<List<LaunchPad>>
    suspend fun getLaunchPadById(id: String): Response<LaunchPad>

    suspend fun getLandingPads(): Response<List<LandingPad>>
    suspend fun getLandingPadById(id: String): Response<LandingPad>

    suspend fun getRockets(): Response<List<Rocket>>
    suspend fun getRocketById(id: String): Response<Rocket>

    suspend fun getLaunches(): Response<ApiResponse<Launches>>
    suspend fun getLaunchById(id: String): Response<Launch>

    suspend fun translate(source: String): Response<PlainTextResponse>
}