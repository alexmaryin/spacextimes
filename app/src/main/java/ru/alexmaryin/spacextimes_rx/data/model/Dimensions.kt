package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.enums.OrbitType

data class Mass(
    val kg: Int,
    val lb: Int
)

data class Volume(
    @SerializedName("cubic_meters") val inMeters: Int,
    @SerializedName("cubic_feet") val inFeet: Int
)

data class LineSize(
    val meters: Float,
    val feet: Float
)

data class Thrust(
    val kN: Float,
    val lbf: Int
)

data class Isp(
    val vacuum: Int,
    @SerializedName("sea_level") val seaLevel: Int,
)

data class PayloadWeight(
    val id: OrbitType,
    val name: String,
    val kg: Int,
    val lb: Int,
)