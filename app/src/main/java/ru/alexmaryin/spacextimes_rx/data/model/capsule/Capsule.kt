package ru.alexmaryin.spacextimes_rx.data.model.capsule

import com.google.gson.annotations.SerializedName
import java.util.*

data class Capsule(
    val serial: String,
    val status: String,
    val type: String,
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

enum class CapsuleStatus(val serialized: String) {
    UNKNOWN("unknown"), ACTIVE("active"), RETIRED("retired"), DESTROYED("destroyed")
}

enum class CapsuleType(val serialized: String) {
    DRAGON1_0("Dragon 1.0"), DRAGON1_1("Dragon 1.1"), DRAGON2_0("Dragon 2.0")
}
