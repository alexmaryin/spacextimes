package ru.alexmaryin.spacextimes_rx.data.api.translator

import android.util.Log
import kotlinx.coroutines.*
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.Api
import javax.inject.Inject

class TranslatorApiImpl @Inject constructor(val api: Api) : TranslatorApi {

    private lateinit var response: Response<PlainTextResponse>

    override fun translate(source: String): String? {
        runBlocking {
            response = api.translate(source)
        }
        return if (response.isSuccessful) {
            (response.body() as PlainTextResponse).data
        } else null
    }
}