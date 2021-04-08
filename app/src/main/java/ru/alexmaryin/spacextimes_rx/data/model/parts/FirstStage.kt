package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.extra.Thrust

data class FirstStage(
    val engines: Int,
    val reusable: Boolean,
    @SerializedName("thrust_sea_level") val thrustSeaLevel: Thrust,
    @SerializedName("thrust_vacuum") val thrustVacuum: Thrust,
    @SerializedName("fuel_amount_tons") val fuelAmount: Float,
    @SerializedName("burn_time_sec") val burnTime: Int,
)