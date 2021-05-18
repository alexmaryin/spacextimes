package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.FairingStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.FairingVersion

@JsonClass(generateAdapter = true)
data class Fairing(
    override val id: String,
    val serial: String,
    val version: FairingVersion?,
    val status: FairingStatus,
    val launches: List<String> = emptyList(),
    @Json(name = "reuse_count") val reuseCount: Int,
    @Json(name = "net_landing_attempts") val catchesAttempts: Int,
    @Json(name = "net_landing") val catchesSuccess: Int,
    @Json(name = "water_landing_attempts") val swimAttempts: Int,
    @Json(name = "water_landing") val swims: Int,
    @Json(name = "last_update") val lastUpdate: String?,
) : HasStringId
