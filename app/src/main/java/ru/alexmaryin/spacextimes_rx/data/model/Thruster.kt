package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName

data class Thruster(
    val type: String,
    val amount: Int,
    val pods: Int,
    val isp: Int,
    val thrust: Thrust,
    @SerializedName("fuel_1") val HotComponent: String,
    @SerializedName("fuel_2") val OxidizerComponent: String
)