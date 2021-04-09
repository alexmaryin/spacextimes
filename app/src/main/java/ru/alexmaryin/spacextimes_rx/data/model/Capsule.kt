package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleType
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches

data class Capsule(
    override val id: String,
    val serial: String,
    val status: CapsuleStatus,
    val type: CapsuleType,
    @SerializedName("reuse_count") val reuseCount: Int,
    @SerializedName("water_landings") val waterLandings: Int,
    @SerializedName("land_landings") val landLandings: Int,
    @SerializedName("last_update") override val lastUpdate: String?,
    override var lastUpdateRu: String?,
    val launches: List<Launches> = emptyList()
) : HasStringId, HasLastUpdate