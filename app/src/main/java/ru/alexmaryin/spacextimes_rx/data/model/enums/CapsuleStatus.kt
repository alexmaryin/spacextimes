package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class CapsuleStatus {
    @Json(name = "active") ACTIVE,
    @Json(name = "unknown") UNKNOWN,
    @Json(name = "retired") RETIRED,
    @Json(name = "destroyed") DESTROYED
}