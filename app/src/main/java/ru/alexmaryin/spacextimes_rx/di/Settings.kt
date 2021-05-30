package ru.alexmaryin.spacextimes_rx.di

import android.util.Log
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.*
import ru.alexmaryin.spacextimes_rx.ProtoSettings
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

const val HOUR_TO_MILLIS = 60 * 60 * 1000   // hours to milliseconds

class SettingsRepository @Inject constructor(val settings: DataStore<ProtoSettings>) {

    val saved = settings.data
    var armSynchronize = false

    suspend fun translateToRu(value: Boolean) = settings.updateData {
        it.toBuilder().setTranslateToRu(value).build()
    }

    suspend fun saveLastSync(cls: String) = settings.updateData {
        it.toBuilder().putLastSync(cls, System.currentTimeMillis()).build()
    }

    suspend fun refreshInterval(hours: Int) = settings.updateData {
        it.toBuilder().setRefreshInterval(hours * HOUR_TO_MILLIS).build()
    }

    fun checkNeedSync(cls: String) = saved.take(1).map { saved ->
        Log.d("REPOSITORY", "Triggered check need sync for $cls")
        saved.lastSyncMap[cls]?.run {
            System.currentTimeMillis() - this > saved.refreshInterval
        } ?: false || armSynchronize
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
object SettingsSerializer : Serializer<ProtoSettings> {
    override val defaultValue: ProtoSettings = ProtoSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoSettings {
        try {
            return ProtoSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: ProtoSettings, output: OutputStream) = t.writeTo(output)
}