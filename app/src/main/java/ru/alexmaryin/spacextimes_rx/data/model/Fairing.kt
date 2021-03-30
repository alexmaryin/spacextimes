package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.FairingStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.FairingVersion

data class Fairing(
    override val id: String,
    val serial: String,
    val version: FairingVersion?,
    val status: FairingStatus,
    val launches: List<String> = emptyList(),
    @SerializedName("reuse_count") val reuseCount: Int,
    @SerializedName("net_landing_attempts") val catchesAttempts: Int,
    @SerializedName("net_landing") val catchesSuccess: Int,
    @SerializedName("water_landing_attempts") val swimAttempts: Int,
    @SerializedName("water_landing") val swims: Int,
    @SerializedName("last_update") val lastUpdate: String?,
) : HasStringId
