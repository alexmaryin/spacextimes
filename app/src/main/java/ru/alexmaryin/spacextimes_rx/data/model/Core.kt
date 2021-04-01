package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.CoreStatus

data class Core(
    override val id: String,
    val serial: String,
    val block: Int?,
    val status: CoreStatus,
    @SerializedName("reuse_count") val reuseCount: Int,
    @SerializedName("rtls_attempts") val groundLandAttempts: Int,
    @SerializedName("rtls_landings") val groundLandings: Int,
    @SerializedName("asds_attempts") val waterLandAttempts: Int,
    @SerializedName("asds_landings") val waterLandings: Int,
    @SerializedName("last_update") override val lastUpdate: String?,
    override var lastUpdateRu: String?,
    val launches: List<Launches> = emptyList(),
) : HasStringId, HasLastUpdate { fun totalFlights() = launches.size }