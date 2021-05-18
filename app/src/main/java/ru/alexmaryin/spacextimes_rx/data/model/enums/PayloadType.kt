package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class PayloadType {
    @Json(name = "Satellite") SATELLITE,
    @Json(name = "Dragon Boilerplate") DRAGON_BOILERPLATE,
    @Json(name = "Dragon 1.0") DRAGON_1_0,
    @Json(name = "Dragon 1.1") DRAGON_1_1,
    @Json(name = "Dragon 2.0") DRAGON_2_0,
    @Json(name = "Crew Dragon") CREW_DRAGON,
    @Json(name = "Lander") LANDER,
}