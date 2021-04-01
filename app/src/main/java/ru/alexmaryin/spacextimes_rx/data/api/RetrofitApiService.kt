package ru.alexmaryin.spacextimes_rx.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorUrls
import ru.alexmaryin.spacextimes_rx.data.model.*

interface RetrofitApiService {

    @GET(SpacexUrls.AllCapsules)
    suspend fun getCapsules(): Response<List<Capsules>>

    @GET(SpacexUrls.AllCapsules+"{id}")
    suspend fun getCapsuleById(@Path("id") id: String): Response<Capsules>

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
    suspend fun getLaunchPads(): Response<List<LaunchPad>>

    @GET(SpacexUrls.AllLaunchPads+"{id}")
    suspend fun getLaunchPadById(@Path("id") id: String): Response<LaunchPad>

    @GET(SpacexUrls.AllLandingPads)
    suspend fun getLandingPads(): Response<List<LandingPad>>

    @GET(SpacexUrls.AllLandingPads+"{id}")
    suspend fun getLandingPadById(@Path("id") id: String): Response<LandingPad>

    @GET(SpacexUrls.AllRockets)
    suspend fun getRockets(): Response<List<Rocket>>

    @GET(SpacexUrls.AllRockets+"{id}")
    suspend fun getRocketById(@Path("id") id: String): Response<Rocket>

    @POST(SpacexUrls.AllLaunchesQuery)
    suspend fun getLaunches(@Body body: RequestBody): Response<ApiResponse<Launches>>

    @GET(SpacexUrls.AllLaunches+"{id}")
    suspend fun getLaunchById(@Path("id") id: String): Response<Launch>

//    @POST(TranslatorUrls.PlainText)
//    suspend fun translate(@Body body: RequestBody): Response<PlainTextResponse>

    @Multipart
    @POST(TranslatorUrls.FileToText)
    suspend fun translate(@Part("lang") lang: RequestBody, @Part file: MultipartBody.Part): Response<PlainTextResponse>

}