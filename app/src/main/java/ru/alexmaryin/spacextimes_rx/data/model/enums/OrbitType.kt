package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class OrbitType{
    @Json(name = "leo") LOW_EARTH,
    @Json(name = "gto") GEOSYNCHRONOUS,
    @Json(name = "mars") MARS,
    @Json(name = "pluto") PLUTO,
    @Json(name = "moon") MOON
}