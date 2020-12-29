package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Core(
    val serial: String;
    val block: Int;
    val status: CoreStatus;
    @SerializedName("reuse_count") val reuseCount: Int;
    @SerializedName("rtls_attempts") val groundLandAttempts: Int;
    @Serializedname("rtls_landings") val groundLandings: Int;
    @SerializedName("asds_attempts") val waterLandAttempts: Int;
    @Serializedname("asds_landings") val waterLandings: Int;
    @SerializedName("last_update") val lastUpdate: String;
    val launches: List<String> = emptyListy()
)

enum class CoreStatus {
    @SerializedName("active") ACTIVE,
    @SerializedName("inactive") INACTIVE,
    @SerializedName("unknown") UNKNOWN,
    @SerializedName("expended") EXPENDED,
    @SerializedName("lost") LOST,
    @SerializedName("retired") RETIRED
}