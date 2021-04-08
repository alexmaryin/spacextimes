package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.extra.Isp
import ru.alexmaryin.spacextimes_rx.data.model.extra.Thrust

data class Engine(
    val isp: Isp,
    val number: Int,
    val type: String,
    val version: String,
    val layout: String,
    @SerializedName("thrust_sea_level") val thrustSeaLevel: Thrust,
    @SerializedName("thrust_vacuum") val thrustVacuum: Thrust,
    @SerializedName("engine_loss_max") val engineLossMax: Int,
    @SerializedName("propellant_1") val oxidizerComponent: String,
    @SerializedName("propellant_2") val hotComponent: String,
    @SerializedName("thrust_to_weight") val thrustToWeight: Float,
)