package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleType
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches
import ru.alexmaryin.spacextimes_rx.data.room_model.CapsuleLocal

@JsonClass(generateAdapter = true)
data class Capsule(
    override val id: String,
    val serial: String,
    val status: CapsuleStatus,
    val type: CapsuleType,
    @Json(name = "reuse_count") val reuseCount: Int,
    @Json(name = "water_landings") val waterLandings: Int,
    @Json(name = "land_landings") val landLandings: Int,
    @Json(name = "last_update") override val lastUpdate: String?,
    @Transient override var lastUpdateRu: String? = null,
    val launches: List<Launches> = emptyList()
) : HasStringId, HasLastUpdate {
    fun toRoom() = CapsuleLocal(id, serial, status, type, reuseCount, waterLandings, landLandings, lastUpdate, lastUpdateRu)

    val totalFlights: Int get() = when {
        launches.isNotEmpty() -> launches.size
        reuseCount > 0 -> reuseCount + 1
        else -> waterLandings + landLandings
    }
}