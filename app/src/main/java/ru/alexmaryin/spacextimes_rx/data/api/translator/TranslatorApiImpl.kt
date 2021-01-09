package ru.alexmaryin.spacextimes_rx.data.api.translator

import okio.IOException
import ru.alexmaryin.spacextimes_rx.data.api.Api
import javax.inject.Inject

class TranslatorApiImpl @Inject constructor(val api: Api) : TranslatorApi {

    override suspend fun translate(source: String): String? = try {
        val response = api.translate(source)
        if (response.isSuccessful) response.body()?.data else null
    } catch (e: IOException) { null }
}