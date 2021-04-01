package ru.alexmaryin.spacextimes_rx.data.api.translator

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.local.TranslateDao
import ru.alexmaryin.spacextimes_rx.data.local.TranslateItem
import java.io.File
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

class TranslatorApiImpl @Inject constructor(
    val api: SpaceXApi,
    private val translationsDao: TranslateDao,
    @ApplicationContext val appContext: Context,
    ) : TranslatorApi {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun fromString(source: String): String? {
        val file = File.createTempFile("translate${source.hashCode()}", ".txt", appContext.cacheDir).apply {
            writeText(source)
        }
        val response = api.translate(file)
        file.delete()
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
            // At first try to load local copy of translation for the specified string
            items.forEach { to.set(it, translationsDao.get(from.get(it).hashCode())?.translation) }
            // Then put to list items which have field for translate and have no translated field yet
            val listForTranslating = items.filter { from.get(it) != null && to.get(it) == null }
            if (listForTranslating.isNotEmpty()) {
                fromList(listForTranslating, readItemToTranslate = { from.get(it)!! },
                    updateItemWithTranslate = { item, translate ->
                        to.set(item, translate)
                        // At finish save fetched translations locally
                        translationsDao.insert(TranslateItem(
                            id = from.get(item).hashCode(),
                            origin = from.get(item)!!,
                            translation = translate,
                            insertDate = Date()
                        ))
                    })
            }
        }
    }
}
