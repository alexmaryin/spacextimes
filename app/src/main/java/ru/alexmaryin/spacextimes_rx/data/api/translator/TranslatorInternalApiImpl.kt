package ru.alexmaryin.spacextimes_rx.data.api.translator

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.local.translations.TranslateDao
import ru.alexmaryin.spacextimes_rx.data.local.translations.TranslateItem
import ru.alexmaryin.spacextimes_rx.utils.toChunkedList
import java.io.File
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

class TranslatorInternalApiImpl @Inject constructor(
    val api: SpaceXApi,
    private val translationsDao: TranslateDao,
    @ApplicationContext val appContext: Context,
) : TranslatorInternalApi {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun fromString(source: String): String? =
        withContext(Dispatchers.IO) {
            val file = File.createTempFile("translate${source.hashCode()}", ".txt", appContext.cacheDir).apply {
                writeText(source)
            }
            val response = api.translate(file)
            file.delete()
            if (response.isSuccessful) response.body()?.data else throw IOException(response.message())
        }

    @ExperimentalStdlibApi
    override suspend fun <T> fromList(
        source: List<T>,
        readItemToTranslate: (T) -> String,
        updateItemWithTranslate: (T, String) -> Unit
    ) {
        var counter = 0
        source.map(readItemToTranslate).toChunkedList().forEach { part ->
            fromString(part.joinToString("\n"))?.let {
                it.split("\n").forEach { translatedString ->
                    updateItemWithTranslate(source[counter], translatedString)
                    Log.d("TRANSLATOR", "Выполнен перевод для элемента $counter:\nFROM : ${readItemToTranslate(source[counter])}\nTO : $translatedString}")
                    translationsDao.insert(
                        TranslateItem(
                        origin = readItemToTranslate(source[counter]),
                        translation = translatedString,
                        insertDate = Date())
                    )
                    counter++
                }
            }
        }
    }

    @ExperimentalStdlibApi
    override suspend fun <T> translate(
        context: CoroutineContext,
        items: List<T>,
        from: KProperty1<T, String?>,
        to: KMutableProperty1<T, String?>
    ) = withContext(context + Dispatchers.IO) {
        val listForTranslating = items.filter { from.get(it) != null && to.get(it) == null }
        if (listForTranslating.isNotEmpty()) {
            fromList(listForTranslating, readItemToTranslate = { from.get(it)!! },
                updateItemWithTranslate = { item, translate -> to.set(item, translate) })
        }
    }

    override suspend fun <T> tryLoadLocalTranslate(
        context: CoroutineContext,
        items: List<T>,
        from: KProperty1<T, String?>,
        to: KMutableProperty1<T, String?>
    ) = withContext(context + Dispatchers.IO) {
        items.forEach { from.get(it)?.let { origin -> to.set(it, translationsDao.findString(origin)?.translation) } }
    }
}


