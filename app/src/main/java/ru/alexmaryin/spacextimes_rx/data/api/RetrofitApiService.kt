package ru.alexmaryin.spacextimes_rx.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorUrls
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.lists.*

interface RetrofitApiService {

    @GET(SpacexUrls.AllCapsules)
    suspend fun getCapsules(): Response<List<Capsules>>

    @POST(SpacexUrls.CapsuleQuery)
    suspend fun getCapsuleById(@Body body: RequestBody): Response<ApiResponse<Capsule>>

    @GET(SpacexUrls.AllCores)
    suspend fun getCores(): Response<List<Cores>>

    @POST(SpacexUrls.CoreQuery)
    suspend fun getCoreById(@Body body: RequestBody): Response<ApiResponse<Core>>

    @GET(SpacexUrls.AllCrew)
    suspend fun getCrew(): Response<List<Crews>>

    @POST(SpacexUrls.CrewQuery)
    suspend fun getCrewById(@Body body: RequestBody): Response<ApiResponse<Crew>>

    @GET(SpacexUrls.AllDragons)
    suspend fun getDragons(): Response<List<Dragon>>

    @GET(SpacexUrls.AllDragons+"{id}")
    suspend fun getDragonById(@Path("id") id: String): Response<Dragon>

    @GET(SpacexUrls.AllLaunchPads)
    suspend fun getLaunchPads(): Response<List<LaunchPads>>

    @POST(SpacexUrls.LaunchPadQuery)
    suspend fun getLaunchPadById(@Body body: RequestBody): Response<ApiResponse<LaunchPad>>

    @GET(SpacexUrls.AllLandingPads)
    suspend fun getLandingPads(): Response<List<LandingPads>>

    @POST(SpacexUrls.LandingPadQuery)
    suspend fun getLandingPadById(@Body body: RequestBody): Response<ApiResponse<LandingPad>>

    @GET(SpacexUrls.AllRockets)
    suspend fun getRockets(): Response<List<Rocket>>

    @GET(SpacexUrls.AllRockets+"{id}")
    suspend fun getRocketById(@Path("id") id: String): Response<Rocket>

    @POST(SpacexUrls.AllLaunchesQuery)
    suspend fun getLaunches(@Body body: RequestBody): Response<ApiResponse<Launches>>

    @POST(SpacexUrls.AllLaunchesQuery)
    suspend fun getLaunchById(@Body body: RequestBody): Response<ApiResponse<Launch>>

    @POST(SpacexUrls.PayloadQuery)
    suspend fun getPayloadById(@Body body: RequestBody): Response<ApiResponse<Payload>>

    @GET(SpacexUrls.AllHistoryEvents)
    suspend fun getHistoryEvents(): Response<List<History>>

    @Multipart
    @POST(TranslatorUrls.FileToText)
    suspend fun translate(@Part("lang") lang: RequestBody, @Part file: MultipartBody.Part): Response<PlainTextResponse>

}