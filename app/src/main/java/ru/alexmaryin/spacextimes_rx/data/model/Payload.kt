package ru.alexmaryin.spacextimes_rx.data.model

import android.content.Context
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.PayloadType
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadDragon
import ru.alexmaryin.spacextimes_rx.data.room_model.PayloadLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.PayloadWithoutDragon
import java.util.*

@JsonClass(generateAdapter = true)
data class Payload(
    override val id: String,
    val name: String?,
    val type: PayloadType?,
    val reused: Boolean,
    val customers: List<String> = emptyList(),
    val nationalities: List<String> = emptyList(),
    val manufacturers: List<String> = emptyList(),
    val orbit: String?,
    val regime: String?,
    val longitude: Float?,
    val eccentricity: Float?,
    val epoch: Date?,
    var dragon: PayloadDragon,
    @Json(name = "semi_major_axis_km") val semiAxis: Float?,
    @Json(name = "raan") val rightAscension: Float?,
    @Json(name = "periapsis_km") val periapsis: Float?,
    @Json(name = "apoapsis_km") val apoapsis: Float?,
    @Json(name = "inclination_deg") val inclination: Float?,
    @Json(name = "arg_of_pericenter") val pericenterArg: Float?,
    @Json(name = "lifespan_years") val lifeSpan: Int?,
    @Json(name = "period_min") val period: Float?,
    @Json(name = "mean_motion") val meanMotion: Float?,
    @Json(name = "mean_anomaly") val meanAnomaly: Float?,
    @Json(name = "mass_kg") val massInKg: Float?,
    @Json(name = "mass_lbs") val massInLbs: Float?,
    @Json(name = "reference_system") val referenceSystem: String?,
    @Json(name = "norad_ids") val norads: List<Int> = emptyList(),
) : HasStringId {
    fun typeAsString(res: Context) = type?.let {
        res.getString(
            when (it) {
                PayloadType.SATELLITE -> R.string.satellite_text
                PayloadType.DRAGON_BOILERPLATE -> R.string.dragon_boilerplate_text
                PayloadType.DRAGON_1_0 -> R.string.dragon_1_0_text
                PayloadType.DRAGON_1_1 -> R.string.dragon_1_1_text
                PayloadType.DRAGON_2_0 -> R.string.dragon_2_0_text
                PayloadType.CREW_DRAGON -> R.string.crew_dragon_text
                PayloadType.LANDER -> R.string.lander_text
            }
        )
    }

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

    val isOrbitDataPresent get() = (eccentricity ?: semiAxis ?: inclination ?: longitude ?: pericenterArg ?: rightAscension ?:
            meanAnomaly ?: meanMotion ?: epoch) != null

    fun toRoom() = PayloadLocal(
        payload = PayloadWithoutDragon(id, name, type, reused, customers, nationalities, manufacturers, orbit, regime, longitude,
            eccentricity, epoch, id, semiAxis, rightAscension, periapsis, apoapsis, inclination, pericenterArg,
            lifeSpan, period, meanMotion, meanAnomaly, massInKg, massInLbs, referenceSystem, norads),
        dragon = dragon.toRoom(id)
    )



}