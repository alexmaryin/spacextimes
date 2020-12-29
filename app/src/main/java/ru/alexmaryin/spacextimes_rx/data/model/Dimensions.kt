package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName

data class Mass(
    val kg: Int,
    val lb: Int
)

data class Volume(
    @SerializedName("cubic_meters") val inMeters: Int,
    @SerializedName("cubic_feet") val inFeet: Int
)

data class LineSize(
    val meters: Int,
    val feet: Int
)

data class Thrust(
    val kN: Float,
    val lbf: Int
)
