package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.enums.OrbitType

@JsonClass(generateAdapter = true)
data class Mass(
    val kg: Int,
    val lb: Int
)

@JsonClass(generateAdapter = true)
data class Volume(
    @Json(name = "cubic_meters") val inMeters: Int,
    @Json(name = "cubic_feet") val inFeet: Int
)

@JsonClass(generateAdapter = true)
data class LineSize(
    val meters: Float?,
    val feet: Float?
)

@JsonClass(generateAdapter = true)
data class Thrust(
    val kN: Float?,
    val lbf: Int?
)

@JsonClass(generateAdapter = true)
data class Isp(
    val vacuum: Int?,
    @Json(name = "sea_level") val seaLevel: Int?,
)

@JsonClass(generateAdapter = true)
data class PayloadWeight(
    val id: OrbitType,
    val name: String,
    val kg: Float?,
    val lb: Float?,
)