package ru.alexmaryin.spacextimes_rx.data.api.translator

import android.content.Context
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.buffer
import okio.sink
import okio.use
import ru.alexmaryin.spacextimes_rx.BuildConfig
import ru.alexmaryin.spacextimes_rx.data.api.local.translations.TranslateAsset
import ru.alexmaryin.spacextimes_rx.data.api.local.translations.TranslateDao
import ru.alexmaryin.spacextimes_rx.data.api.local.translations.TranslateItem
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasTitle
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.*
import java.io.File
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

@ExperimentalStdlibApi
class TranslatorImpl @Inject constructor(
    private val settings: Settings,
    private val translationsDao: TranslateDao,
    private val apiRemote: ApiFastTranslator,
    @ApplicationContext val appContext: Context,
) : TranslatorApi {

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun List<String>.translateByFile() = run {
        val file = File.createTempFile("translate${hashCode()}", ".txt", appContext.cacheDir).apply {
            writeText(joinToString("\n"))
        }
        val response = apiRemote.translate(
            "en-ru".toRequestBody("application/json".toMediaTypeOrNull()),
            MultipartBody.Part.createFormData("file", file.path, file.asRequestBody("text/plain".toMediaTypeOrNull()))
        )
        file.delete()
        if (response.isSuccessful) zip(response.body()?.data!!.split("\n"))
        else throw IOException(response.message())
    }

    private suspend fun <T> List<T>.tryLoadCachedTranslate(
        from: KProperty1<T, String?>,
        to: KMutableProperty1<T, String?>
    ) = map { item ->
        from.get(item)?.let { origin ->
            to.set(item, translationsDao.findString(origin)?.translation)
        }
        item
    }

    private suspend fun <T> List<T>.tryRemoteTranslate(
        from: KProperty1<T, String?>,
        to: KMutableProperty1<T, String?>
    ) = apply {
        filter { to.get(it) == null }
            .mapNotNull { from.get(it) }
            .toChunkedList()
            .filterNot { it.isEmpty() }
            .forEach { part ->
                part.translateByFile().forEach { (origin, translated) ->
                    to.set(find { from.get(it) == origin }!!, translated)
                    translationsDao.insert(TranslateItem(origin = origin, translation = translated, insertDate = Date()))
                }
            }
        if(BuildConfig.DEBUG) backupTranslations()
    }

    private inline fun <reified T> Flow<Result>.doTranslate(
        from: KProperty1<T, String?>,
        to: KMutableProperty1<T, String?>,
    ) = combine(settings.saved) { result, saved ->
        if (result is Success<*> && saved.translateToRu) {
            restoreFromBackup()
            result.toListOf<T>()?.let { items ->
                items
                    .tryLoadCachedTranslate(from, to)
                    .tryRemoteTranslate(from, to)
                if (result.isSingleData<T>()) items.toSingleSuccess() else items.toSuccess()
            } ?: throw TypeCastException("This list has not implement interface ${T::class.simpleName}")
        } else {
            result
        }
    }
        .flowOn(Dispatchers.IO)
        .catch { e ->
            if (e is IOException) {
                emit(Error("Translator error: ${e.localizedMessage}", ErrorType.REMOTE_TRANSLATOR_ERROR))
                if(BuildConfig.DEBUG) backupTranslations()
            }
        }


    override fun Flow<Result>.translateDetails(): Flow<Result> = doTranslate(HasDetails::details, HasDetails::detailsRu)

    override fun Flow<Result>.translateLastUpdate(): Flow<Result> = doTranslate(HasLastUpdate::lastUpdate, HasLastUpdate::lastUpdateRu)

    override fun Flow<Result>.translateDescription(): Flow<Result> = doTranslate(HasDescription::description, HasDescription::descriptionRu)

    override fun Flow<Result>.translateTitle(): Flow<Result> = doTranslate(HasTitle::title, HasTitle::titleRu)

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun backupTranslations(): Boolean {
        val data = translationsDao.selectAll()
        val version = settings.saved.last().assetVersion + 1
        return if (data.isNotEmpty()) {
            val file = File.createTempFile("translations", ".json")
            file.sink().buffer().use { sink ->
                with(JsonWriter.of(sink)) {
                    indent = "    "
                    beginObject()
                    name("version").value(version)
                    name("items")
                    beginArray()
                    data.map { item ->
                        beginObject()
                        name("origin").value(item.origin)
                        name("translation").value(item.translation)
                        endObject()
                    }
                    endArray()
                    endObject()
                    close()
                }
            }
            Log.d("ASSETS", "Translations db version $version was back up to file: ${file.name}")
            true
        } else {
            Log.d("ASSETS", "Translations db has not created due to empty database")
            false
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun restoreFromBackup() = try {
        Moshi.Builder().build().apply {
            val adapter = adapter(TranslateAsset::class.java)
            appContext.assets.open(BuildConfig.TRANSLATE_ASSET).bufferedReader().use {
                adapter.lenient().fromJson(it.readText())?.let { asset ->
                    if (asset.version > settings.saved.last().assetVersion) {
                        Log.d("ASSETS", "Loaded from json ${asset.items.size} records")
                        translationsDao.insertAll(asset.items.map { item ->
                            val id = translationsDao.findString(item.origin)?.rowId
                            TranslateItem(id, item.origin, item.translation, Date(System.currentTimeMillis()))
                        })
                        settings.assetVersion(asset.version)
                    } else {
                        Log.d("ASSETS", "Asset version ${asset.version} already has been restored")
                        return false
                    }
                }
            }
        }
        Log.d("ASSETS", "Successful insert to database")
        true
    } catch (e: Exception) {
        when (e) {
            is JsonDataException ->  {
                FirebaseCrashlytics.getInstance().log("Json format of translations file is corrupted")
                Log.d("ASSETS", "Json format of translations file is corrupted")
            }
            is IOException -> Log.e("ASSETS", "File with translations assets has not found: ${BuildConfig.TRANSLATE_ASSET}")
            else -> FirebaseCrashlytics.getInstance().log(e.stackTraceToString())
        }
        false
    }
}