package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cargo(
    @Json(name = "solar_array") val solarArray: Int,
    @Json(name = "unpressurized_cargo") val unpressurized: Boolean
)