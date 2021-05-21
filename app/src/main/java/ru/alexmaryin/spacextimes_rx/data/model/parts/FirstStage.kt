package ru.alexmaryin.spacextimes_rx.data.model.parts

import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Thrust

@JsonClass(generateAdapter = true)
data class FirstStage(
    val engines: Int,
    val reusable: Boolean,
    @Embedded @Json(name = "thrust_sea_level") val thrustSeaLevel: Thrust,
    @Embedded @Json(name = "thrust_vacuum") val thrustVacuum: Thrust,
    @Json(name = "fuel_amount_tons") val fuelAmount: Float?,
    @Json(name = "burn_time_sec") val burnTime: Int?,
)