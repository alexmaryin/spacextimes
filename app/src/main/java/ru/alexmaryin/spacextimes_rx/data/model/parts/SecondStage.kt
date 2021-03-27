package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.Thrust

data class SecondStage(
    val thrust: Thrust,
    val payloads: Payloads,
    val reusable: Boolean,
    val engines: Int,
    @SerializedName("fuel_amount_tons") val fuelAmount: Float,
    @SerializedName("burn_time_sec") val burnTime: Int,
)