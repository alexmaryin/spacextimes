package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class FairingStatus {
    @Json(name = "active") ACTIVE,
    @Json(name = "inactive") INACTIVE,
    @Json(name = "unknown") UNKNOWN,
    @Json(name = "expended") EXPENDED,
    @Json(name = "lost") LOST,
    @Json(name = "retired") RETIRED
}