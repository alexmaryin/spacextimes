package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.Isp
import ru.alexmaryin.spacextimes_rx.data.model.Thrust

data class Engine(
    val isp: Isp,
    @SerializedName("thrust_sea_level") val thrustSeaLevel: Thrust,
    @SerializedName("thrust_vacuum") val thrustVacuum: Thrust,
    val number: Int,
    val type: String,
    val version: String,
    val layout: String,
    @SerializedName("engine_loss_max") val engineLossMax: Int,
    @SerializedName("propellant_1") val OxidizerComponent: String,
    @SerializedName("propellant_2") val HotComponent: String,
    @SerializedName("thrust_to_weight") val thrustToWeight: Float,
)