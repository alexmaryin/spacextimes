package ru.alexmaryin.spacextimes_rx.di

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

const val SYNC_INTERVAL = 7200000   // 2 hour for sync interval

@Singleton
class Settings @Inject constructor() {
    var translateToRu: Boolean = false
    var armedSynchronize: Boolean = false
    var lastSync: Map<String, Long> = emptyMap()

    fun needSyncFor(cls: String) = lastSync[cls]?.run {
        System.currentTimeMillis() - this > SYNC_INTERVAL
    } ?: false || armedSynchronize
}

object SettingsSerializer : Serializer<ProtoSettings> {
    override val defaultValue = ProtoSettings.getDefaultvalue()

    override suspend fun readFrom(input: InputStream): ProtoSettings {
        try {
            return ProtoSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: ProtoSettings, output: OutputStream) = t.writeTo(output)
}