package ru.alexmaryin.spacextimes_rx.data.api.remote

import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.model.api.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiResponse
import ru.alexmaryin.spacextimes_rx.data.model.lists.*
import java.io.File

interface SpaceXApi {

    suspend fun getCapsules(): Response<ApiResponse<List<Capsule>>>
    suspend fun getCapsuleById(id: String): Response<ApiResponse<Capsule>>

    suspend fun getCores(): Response<ApiResponse<List<Core>>>
    suspend fun getCoreById(id: String): Response<ApiResponse<Core>>

    suspend fun getCrew(): Response<ApiResponse<List<Crew>>>
    suspend fun getCrewById(id: String): Response<ApiResponse<Crew>>

    suspend fun getDragons(): Response<List<Dragon>>
    suspend fun getDragonById(id: String): Response<Dragon>

    suspend fun getLaunchPads(): Response<List<LaunchPads>>
    suspend fun getLaunchPadById(id: String): Response<ApiResponse<LaunchPad>>

    suspend fun getLandingPads(): Response<ApiResponse<List<LandingPad>>>
    suspend fun getLandingPadById(id: String): Response<ApiResponse<LandingPad>>

    suspend fun getRockets(): Response<List<Rocket>>
    suspend fun getRocketById(id: String): Response<Rocket>

    suspend fun getLaunches(): Response<ApiResponse<Launches>>
    suspend fun getLaunchById(id: String): Response<ApiResponse<Launch>>

    suspend fun getPayloadById(id: String): Response<ApiResponse<Payload>>

    suspend fun getHistoryEvents(): Response<List<History>>

    suspend fun translate(file: File): Response<PlainTextResponse>
}