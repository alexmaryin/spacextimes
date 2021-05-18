package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class PadStatus {
    @Json(name = "unknown") UNKNOWN,
    @Json(name = "active") ACTIVE,
    @Json(name = "inactive") INACTIVE,
    @Json(name = "retired") RETIRED,
    @Json(name = "lost") LOST,
    @Json(name = "under construction") UNDER_CONSTRUCTION,
}