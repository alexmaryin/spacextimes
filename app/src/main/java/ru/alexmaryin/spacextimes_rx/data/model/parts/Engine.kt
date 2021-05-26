package ru.alexmaryin.spacextimes_rx.data.model.parts

import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Isp
import ru.alexmaryin.spacextimes_rx.data.model.extra.Thrust

@JsonClass(generateAdapter = true)
data class Engine(
    @Embedded val isp: Isp,
    val number: Int,
    val type: String,
    val version: String,
    val layout: String?,
    @Embedded(prefix = "seaLevel") @Json(name = "thrust_sea_level") val thrustSeaLevel: Thrust,
    @Embedded(prefix = "vacuumLevel") @Json(name = "thrust_vacuum") val thrustVacuum: Thrust,
    @Json(name = "engine_loss_max") val engineLossMax: Int?,
    @Json(name = "propellant_1") val oxidizerComponent: String,
    @Json(name = "propellant_2") val hotComponent: String,
    @Json(name = "thrust_to_weight") val thrustToWeight: Float,
)