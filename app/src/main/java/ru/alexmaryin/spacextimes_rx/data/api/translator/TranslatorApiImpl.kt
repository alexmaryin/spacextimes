package ru.alexmaryin.spacextimes_rx.data.api.translator

import ru.alexmaryin.spacextimes_rx.data.api.Api
import java.io.IOException
import javax.inject.Inject

class TranslatorApiImpl @Inject constructor(val api: Api) : TranslatorApi {

    override suspend fun translate(source: String): String? {
        val response = api.translate(source)
        return if (response.isSuccessful) response.body()?.data
            else throw IOException(response.message())
    }
}