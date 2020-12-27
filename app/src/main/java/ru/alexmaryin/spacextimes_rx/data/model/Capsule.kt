package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Capsule(
    val serial: String,
    val status: CapsuleStatus,
    val type: CapsuleType,
    @SerializedName("dragon")
    val id: UUID,
    @SerializedName("reuse_count")
    val reuseCount: Int,
    @SerializedName("water_landings")
    val waterLandings: Int,
    @SerializedName("land_landings")
    val landLandings: Int,
    @SerializedName("last_update")
    val lastUpdate: String,
    val launches: List<String> = emptyList()

)

enum class CapsuleStatus {
    @SerializedName("unknown") UNKNOWN,
    @SerializedName("active") ACTIVE,
    @SerializedName("retired") RETIRED,
    @SerializedName("destroyed") DESTROYED
}

enum class CapsuleType {
    @SerializedName("Dragon 1.0") DRAGON1_0,
    @SerializedName("Dragon 1.1") DRAGON1_1,
    @SerializedName("Dragon 2.0") DRAGON2_0
}
