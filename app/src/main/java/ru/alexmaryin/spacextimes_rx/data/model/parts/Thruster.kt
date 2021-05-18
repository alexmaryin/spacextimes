package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Thrust

@JsonClass(generateAdapter = true)
data class Thruster(
    val type: String,
    val amount: Int,
    val pods: Int,
    val isp: Int,
    val thrust: Thrust,
    @Json(name = "fuel_1") val HotComponent: String,
    @Json(name = "fuel_2") val OxidizerComponent: String
)