package ru.alexmaryin.spacextimes_rx.di

import android.util.Log
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.transform
import ru.alexmaryin.spacextimes_rx.ProtoSettings
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

const val SYNC_INTERVAL = 7200000   // 2 hour for sync interval

class SettingsRepository @Inject constructor(val settings: DataStore<ProtoSettings>) {

    val saved get() = settings.data
    var armSynchronize = false

    suspend fun translateToRu(value: Boolean) = settings.updateData {
        it.toBuilder().setTranslateToRu(value).build()
    }

    suspend fun saveLastSync(cls: String) = settings.updateData {
        it.toBuilder().putLastSync(cls, System.currentTimeMillis()).build()
    }

    fun checkNeedSync(cls: String) = saved.transform {
        Log.d("REPOSITORY", "Triggered check need sync for $cls")
        emit(it.lastSyncMap[cls]?.run {
            System.currentTimeMillis() - this > SYNC_INTERVAL
        } ?: false || armSynchronize)
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