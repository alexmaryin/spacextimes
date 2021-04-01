package ru.alexmaryin.spacextimes_rx.data.api.translator

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    override suspend fun fromString(source: String): String? =
        withContext(Dispatchers.IO) {
            val file = File.createTempFile("translate${source.hashCode()}", ".txt", appContext.cacheDir).apply {
                writeText(source)
            }
            val response = api.translate(file)
            file.delete()
            if (response.isSuccessful) response.body()?.data else throw IOException(response.message())
        }

    override suspend fun <T> fromList(
        source: List<T>,
        readItemToTranslate: (T) -> String,
        updateItemWithTranslate: (T, String) -> Unit
    ): List<T>? {
        val response = fromString(source.joinToString("\n") { readItemToTranslate(it) })?.split("\n")
        return source.takeIf { source.size == response?.size }
            ?.apply { for ((item, ruString) in (this zip response!!)) updateItemWithTranslate(item, ruString) }
    }

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

    override suspend fun <T> saveLocalTranslations(
        context: CoroutineContext,
        items: List<T>,
        from: KProperty1<T, String?>,
        to: KMutableProperty1<T, String?>
    ) = withContext(context + Dispatchers.IO) {
        items.filter { from.get(it) != null && to.get(it) != null }.forEach {
            translationsDao.insert(TranslateItem(
                    origin = from.get(it)!!,
                    translation = to.get(it)!!,
                    insertDate = Date()))
        }
    }
}
