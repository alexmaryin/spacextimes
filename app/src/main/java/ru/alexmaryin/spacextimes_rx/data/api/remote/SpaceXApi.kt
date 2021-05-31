package ru.alexmaryin.spacextimes_rx.data.api.remote

import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiResponse

interface SpaceXApi {

    suspend fun getCapsules(): Response<ApiResponse<Capsule>>
    suspend fun getCapsuleById(id: String): Response<ApiResponse<Capsule>>

    suspend fun getCores(): Response<ApiResponse<Core>>
    suspend fun getCoreById(id: String): Response<ApiResponse<Core>>

    suspend fun getCrew(): Response<ApiResponse<Crew>>
    suspend fun getCrewById(id: String): Response<ApiResponse<Crew>>

    suspend fun getDragons(): Response<ApiResponse<Dragon>>
    suspend fun getDragonById(id: String): Response<ApiResponse<Dragon>>

    suspend fun getLaunchPads(): Response<ApiResponse<LaunchPad>>
    suspend fun getLaunchPadById(id: String): Response<ApiResponse<LaunchPad>>

    suspend fun getLandingPads(): Response<ApiResponse<LandingPad>>
    suspend fun getLandingPadById(id: String): Response<ApiResponse<LandingPad>>

    suspend fun getRockets(): Response<ApiResponse<Rocket>>
    suspend fun getRocketById(id: String): Response<ApiResponse<Rocket>>

    suspend fun getLaunches(): Response<ApiResponse<Launch>>
    suspend fun getLaunchById(id: String): Response<ApiResponse<Launch>>

    suspend fun getPayloadById(id: String): Response<ApiResponse<Payload>>

    suspend fun getHistoryEvents(): Response<ApiResponse<History>>
}