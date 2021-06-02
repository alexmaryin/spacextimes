package ru.alexmaryin.spacextimes_rx.di

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import ru.alexmaryin.spacextimes_rx.ProtoSettings
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class Settings @Inject constructor(val settings: DataStore<ProtoSettings>) {

    companion object {
        const val HOUR_TO_MILLIS = 60 * 60 * 1000   // hours to milliseconds
        const val TRANSLATE_SWITCH = "translate_to_ru"
        const val REFRESH_INTERVAL_BAR = "refresh_interval"
        const val IS_PREFERENCES_CHANGED = "preferences_changed"
    }

    val saved get() = settings.data.take(1)
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

    fun checkNeedSync(cls: String) = saved.map {
        val result = it.lastSyncMap[cls]?.run {
            System.currentTimeMillis() - this > it.refreshInterval
        } ?: false || armSynchronize
        val str = when {
            armSynchronize -> "armed"
            result && !armSynchronize -> "refresh interval"
            else -> "up to date"
        }
        Log.d("REPOSITORY", "Check need sync for $cls is $result cause of $str")
        result
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
object SettingsSerializer : Serializer<ProtoSettings> {
    override val defaultValue: ProtoSettings = ProtoSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoSettings {
        return try {
            ProtoSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            Log.e("SETTINGS", "Cannot read proto. Create default.")
            defaultValue
        }
    }

    override suspend fun writeTo(t: ProtoSettings, output: OutputStream) = t.writeTo(output)
}