package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadDragon
import java.util.*

data class Payload(
    override val id: String,
    val name: String,
    val type: String,
    val reused: Boolean,
    //val launch: String,     // id of launch
    val customers: List<String> = emptyList(),
    val nationalities: List<String> = emptyList(),
    val manufacturers: List<String> = emptyList(),
    val orbit: String?,
    val regime: String?,
    val longitude: Float?,
    val eccentricity: Float?,
    val epoch: Date?,
    val raan: Float?,
    val dragon: PayloadDragon,
    @SerializedName("semi_major_axis_km") val semiAxis: Float?,
    @SerializedName("periapsis_km") val periapsis: Float?,
    @SerializedName("apoapsis_km") val apoapsis: Float?,
    @SerializedName("inclination_deg") val inclination: Float?,
    @SerializedName("arg_of_pericenter") val pericenterArg: Float?,
    @SerializedName("lifespan_years") val lifeSpan: Int?,
    @SerializedName("period_min") val period: Float?,
    @SerializedName("mean_motion") val meanMotion: Float?,
    @SerializedName("mean_anomaly") val meanAnomaly: Float?,
    @SerializedName("mass_kg") val massInKg: Float,
    @SerializedName("mass_lbs") val massInLbs: Float,
    @SerializedName("reference_system") val referenceSystem: String?,
    @SerializedName("norad_ids") val norads: List<Int> = emptyList(),
) : HasStringId