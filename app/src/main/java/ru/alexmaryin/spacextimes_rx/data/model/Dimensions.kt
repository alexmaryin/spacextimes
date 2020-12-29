package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Mass(
    val kg: Int;
val lb: Int;
)

data class Volume(
    @SerializedName("cubic_meters") val inMeters: Int;
@Serializedname("cubic_feet") val inFeets: Int;
)

data class LineSize(
    val meters: Int;
val feet: Int;
)

data class Thrust(
    val kN: Float;
    val lbf: Int;
)
