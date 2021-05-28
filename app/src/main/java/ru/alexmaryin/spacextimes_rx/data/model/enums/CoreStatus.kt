package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class CoreStatus {
    @Json(name = "unknown") UNKNOWN,
    @Json(name = "active") ACTIVE,
    @Json(name = "inactive") INACTIVE,
    @Json(name = "expended") EXPENDED,
    @Json(name = "lost") LOST,
    @Json(name = "retired") RETIRED
}