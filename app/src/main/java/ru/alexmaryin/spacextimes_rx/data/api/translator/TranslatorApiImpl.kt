package ru.alexmaryin.spacextimes_rx.data.api.translator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.local.TranslateDao
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

class TranslatorApiImpl @Inject constructor(
    val api: SpaceXApi,
    val translationsDao: TranslateDao,
    ) : TranslatorApi {

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

    override suspend fun <T> translate(
        context: CoroutineContext,
        items: List<T>,
        from: KProperty1<T, String?>,
        to: KMutableProperty1<T, String?>
    ) {
        withContext(context + Dispatchers.IO) {
            // Put to list items which have field for translate and have no translated field yet
            val listForTranslating = items.filter { from.get(it) != null && to.get(it) == null }
            fromList(listForTranslating, readItemToTranslate = { from.get(it)!! },
                updateItemWithTranslate = { item, translate -> to.set(item, translate) })
        }
    }
}
