package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class CrewStatus {
    @Json(name = "active") ACTIVE,
    @Json(name = "inactive") INACTIVE,
    @Json(name = "retired") RETIRED,
    @Json(name = "unknown") UNKNOWN
}