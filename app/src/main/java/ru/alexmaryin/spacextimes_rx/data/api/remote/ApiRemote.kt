package ru.alexmaryin.spacextimes_rx.data.api.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiRequest
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiResponse

interface ApiRemote {

    @POST(SpacexUrls.CapsuleQuery)
    suspend fun getCapsules(@Body body: ApiRequest): Response<ApiResponse<Capsule>>

    @POST(SpacexUrls.CapsuleQuery)
    suspend fun getCapsuleById(@Body body: ApiRequest): Response<ApiResponse<Capsule>>

    @POST(SpacexUrls.CoreQuery)
    suspend fun getCores(@Body body: ApiRequest): Response<ApiResponse<Core>>

    @POST(SpacexUrls.CoreQuery)
    suspend fun getCoreById(@Body body: ApiRequest): Response<ApiResponse<Core>>

    @POST(SpacexUrls.CrewQuery)
    suspend fun getCrew(@Body body: ApiRequest): Response<ApiResponse<Crew>>

    @POST(SpacexUrls.CrewQuery)
    suspend fun getCrewById(@Body body: ApiRequest): Response<ApiResponse<Crew>>

    @POST(SpacexUrls.DragonQuery)
    suspend fun getDragons(@Body body: ApiRequest): Response<ApiResponse<Dragon>>

    @POST(SpacexUrls.DragonQuery)
    suspend fun getDragonById(@Body body: ApiRequest): Response<ApiResponse<Dragon>>

    @POST(SpacexUrls.LaunchPadQuery)
    suspend fun getLaunchPads(@Body body: ApiRequest): Response<ApiResponse<LaunchPad>>

    @POST(SpacexUrls.LaunchPadQuery)
    suspend fun getLaunchPadById(@Body body: ApiRequest): Response<ApiResponse<LaunchPad>>

    @POST(SpacexUrls.LandingPadQuery)
    suspend fun getLandingPads(@Body body: ApiRequest): Response<ApiResponse<LandingPad>>

    @POST(SpacexUrls.LandingPadQuery)
    suspend fun getLandingPadById(@Body body: ApiRequest): Response<ApiResponse<LandingPad>>

    @POST(SpacexUrls.RocketQuery)
    suspend fun getRockets(@Body body: ApiRequest): Response<ApiResponse<Rocket>>

    @POST(SpacexUrls.RocketQuery)
    suspend fun getRocketById(@Body body: ApiRequest): Response<ApiResponse<Rocket>>

    @POST(SpacexUrls.AllLaunchesQuery)
    suspend fun getLaunches(@Body body: ApiRequest): Response<ApiResponse<Launch>>

    @POST(SpacexUrls.AllLaunchesQuery)
    suspend fun getLaunchById(@Body body: ApiRequest): Response<ApiResponse<Launch>>

    @POST(SpacexUrls.PayloadQuery)
    suspend fun getPayloadById(@Body body: ApiRequest): Response<ApiResponse<Payload>>

    @POST(SpacexUrls.HistoryQuery)
    suspend fun getHistoryEvents(@Body body: ApiRequest): Response<ApiResponse<History>>
}