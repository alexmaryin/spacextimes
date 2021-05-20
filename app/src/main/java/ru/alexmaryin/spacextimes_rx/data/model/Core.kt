package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.CoreStatus
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches
import ru.alexmaryin.spacextimes_rx.data.room_model.CoreLocal
import kotlin.math.max

@JsonClass(generateAdapter = true)
data class Core(
    override val id: String,
    val serial: String,
    val block: Int?,
    val status: CoreStatus,
    @Json(name = "reuse_count") val reuseCount: Int,
    @Json(name = "rtls_attempts") val groundLandAttempts: Int,
    @Json(name = "rtls_landings") val groundLandings: Int,
    @Json(name = "asds_attempts") val waterLandAttempts: Int,
    @Json(name = "asds_landings") val waterLandings: Int,
    @Json(name = "last_update") override val lastUpdate: String?,
    @Transient override var lastUpdateRu: String? = null,
    val launches: List<Launches> = emptyList(),
) : HasStringId, HasLastUpdate {

    val totalFlights: Int get() = when {
        launches.isNotEmpty() -> launches.size
        reuseCount > 0 -> reuseCount + 1
        else -> max(groundLandAttempts + waterLandAttempts, groundLandings + waterLandings)
    }

    fun toRoom() = CoreLocal(id, serial, block, status, reuseCount, groundLandAttempts, groundLandings,
        waterLandAttempts, waterLandings, lastUpdate, lastUpdateRu)
}
