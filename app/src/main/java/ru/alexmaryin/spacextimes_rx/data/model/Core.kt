package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.CoreStatus
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches

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
) : HasStringId, HasLastUpdate