package ru.alexmaryin.spacextimes_rx.data.api.translator

import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import java.io.IOException
import javax.inject.Inject

class TranslatorApiImpl @Inject constructor(val api: SpaceXApi) : TranslatorApi {

    override suspend fun fromString(source: String): String? {
        val response = api.translate(source)
        return if (response.isSuccessful) response.body()?.data
            else throw IOException(response.message())
    }

    override suspend fun <T> fromList(source: List<T>,
                                      readItemToTranslate: (T) -> String,
                                      updateItemWithTranslate: (T, String) -> Unit): List<T>? {
        val response = fromString(source.joinToString("\n") { readItemToTranslate(it) })?.split("\n")
        return source.takeIf { source.size == response?.size }
            ?.apply { for ((item, ruString) in (this zip response!!)) updateItemWithTranslate(item, ruString) }
    }
}
