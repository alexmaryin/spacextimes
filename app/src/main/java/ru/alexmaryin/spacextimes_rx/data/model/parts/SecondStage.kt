package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Thrust

@JsonClass(generateAdapter = true)
data class SecondStage(
    val thrust: Thrust,
    val payloads: Payloads,
    val reusable: Boolean,
    val engines: Int,
    @Json(name = "fuel_amount_tons") val fuelAmount: Float?,
    @Json(name = "burn_time_sec") val burnTime: Int?,
)