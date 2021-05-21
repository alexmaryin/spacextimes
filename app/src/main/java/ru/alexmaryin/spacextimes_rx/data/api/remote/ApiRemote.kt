package ru.alexmaryin.spacextimes_rx.data.api.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorUrls
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiRequest
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiResponse
import ru.alexmaryin.spacextimes_rx.data.model.api.PlainTextResponse

interface ApiRemote {

    @POST(SpacexUrls.CapsuleQuery)
    suspend fun getCapsules(@Body body: ApiRequest): Response<ApiResponse<List<Capsule>>>

    @POST(SpacexUrls.CapsuleQuery)
    suspend fun getCapsuleById(@Body body: ApiRequest): Response<ApiResponse<Capsule>>

    @POST(SpacexUrls.CoreQuery)
    suspend fun getCores(@Body body: ApiRequest): Response<ApiResponse<List<Core>>>

    @POST(SpacexUrls.CoreQuery)
    suspend fun getCoreById(@Body body: ApiRequest): Response<ApiResponse<Core>>

    @POST(SpacexUrls.CrewQuery)
    suspend fun getCrew(@Body body: ApiRequest): Response<ApiResponse<List<Crew>>>

    @POST(SpacexUrls.CrewQuery)
    suspend fun getCrewById(@Body body: ApiRequest): Response<ApiResponse<Crew>>

    @GET(SpacexUrls.AllDragons)
    suspend fun getDragons(): Response<List<Dragon>>

    @GET(SpacexUrls.AllDragons +"{id}")
    suspend fun getDragonById(@Path("id") id: String): Response<Dragon>

    @POST(SpacexUrls.LaunchPadQuery)
    suspend fun getLaunchPads(@Body body: ApiRequest): Response<ApiResponse<List<LaunchPad>>>

    @POST(SpacexUrls.LaunchPadQuery)
    suspend fun getLaunchPadById(@Body body: ApiRequest): Response<ApiResponse<LaunchPad>>

    @POST(SpacexUrls.LandingPadQuery)
    suspend fun getLandingPads(@Body body: ApiRequest): Response<ApiResponse<List<LandingPad>>>

    @POST(SpacexUrls.LandingPadQuery)
    suspend fun getLandingPadById(@Body body: ApiRequest): Response<ApiResponse<LandingPad>>

    @POST(SpacexUrls.RocketQuery)
    suspend fun getRockets(@Body body: ApiRequest): Response<ApiResponse<List<Rocket>>>

    @GET(SpacexUrls.RocketQuery)
    suspend fun getRocketById(@Body body: ApiRequest): Response<ApiResponse<Rocket>>

    @POST(SpacexUrls.AllLaunchesQuery)
    suspend fun getLaunches(@Body body: ApiRequest): Response<ApiResponse<List<Launch>>>

    @POST(SpacexUrls.AllLaunchesQuery)
    suspend fun getLaunchById(@Body body: ApiRequest): Response<ApiResponse<Launch>>

    @POST(SpacexUrls.PayloadQuery)
    suspend fun getPayloadById(@Body body: ApiRequest): Response<ApiResponse<Payload>>

    @POST(SpacexUrls.HistoryQuery)
    suspend fun getHistoryEvents(@Body body: ApiRequest): Response<ApiResponse<List<History>>>

    @Multipart
    @POST(TranslatorUrls.FileToText)
    suspend fun translate(@Part("lang") lang: RequestBody, @Part file: MultipartBody.Part): Response<PlainTextResponse>

}