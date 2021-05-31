package ru.alexmaryin.spacextimes_rx.data.api.translator

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.alexmaryin.spacextimes_rx.data.model.api.PlainTextResponse

interface ApiFastTranslator {
    @Multipart
    @POST(TranslatorUrls.FileToText)
    suspend fun translate(@Part("lang") lang: RequestBody, @Part file: MultipartBody.Part): Response<PlainTextResponse>
}