package ru.alexmaryin.spacextimes_rx.data.model

import android.content.Context
import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.PayloadType
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadDragon
import java.util.*

data class Payload(
    override val id: String,
    val name: String,
    val type: PayloadType,
    val reused: Boolean,
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
    @SerializedName("lifespan_years") val lifeSpan: Float?,
    @SerializedName("period_min") val period: Float?,
    @SerializedName("mean_motion") val meanMotion: Float?,
    @SerializedName("mean_anomaly") val meanAnomaly: Float?,
    @SerializedName("mass_kg") val massInKg: Float?,
    @SerializedName("mass_lbs") val massInLbs: Float?,
    @SerializedName("reference_system") val referenceSystem: String?,
    @SerializedName("norad_ids") val norads: List<Int> = emptyList(),
) : HasStringId {
    fun typeAsString(res: Context) = res.getString(when(type) {
        PayloadType.SATELLITE -> R.string.satellite_text
        PayloadType.DRAGON_BOILERPLATE -> R.string.dragon_boilerplate_text
        PayloadType.DRAGON_1_0 -> R.string.dragon_1_0_text
        PayloadType.DRAGON_1_1 -> R.string.dragon_1_1_text
        PayloadType.DRAGON_2_0 -> R.string.dragon_2_0_text
        PayloadType.CREW_DRAGON -> R.string.crew_dragon_text
        PayloadType.LANDER -> R.string.lander_text
    })

    fun orbitAsString(res: Context) = orbit?.let {
        res.getString(when(it) {
            "GTO" -> R.string.GTO_string
            "LEO" -> R.string.LEO_string
            "ISS" -> R.string.ISS_string
            "PO" -> R.string.PO_string
            "ES-L1" -> R.string.ES_L1_string
            "SSO" -> R.string.SSO_string
            "HCO" -> R.string.HCO_string
            "HEO" -> R.string.HEO_string
            "MEO" -> R.string.MEO_string
            "VLEO" -> R.string.VLEO_string
            "SO" -> R.string.SO_string
            else -> R.string.unknown_orbit_string
        })
    }

    fun referenceSystemString(res: Context) = referenceSystem?.let {
        res.getString(when(it) {
            "geocentric" -> R.string.geocentric_string
            "heliocentric" -> R.string.heliocentric_string
            "highly-elliptical" -> R.string.highly_elliptical_string
            else -> R.string.unknown_orbit_string
        })
    }
}