package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class LandingPadType {
    @Json(name = "RTLS") LANDING_SITE,
    @Json(name = "ASDS") DRONE_SHIP,
}
