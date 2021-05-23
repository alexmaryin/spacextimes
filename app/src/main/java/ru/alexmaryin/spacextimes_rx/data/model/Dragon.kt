package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.extra.LineSize
import ru.alexmaryin.spacextimes_rx.data.model.extra.Mass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Volume
import ru.alexmaryin.spacextimes_rx.data.model.parts.PressurizedCapsule
import ru.alexmaryin.spacextimes_rx.data.model.parts.Shield
import ru.alexmaryin.spacextimes_rx.data.model.parts.Thruster
import ru.alexmaryin.spacextimes_rx.data.model.parts.Trunk
import ru.alexmaryin.spacextimes_rx.data.room_model.DragonLocal
import java.util.*

@JsonClass(generateAdapter = true)
data class Dragon(
    override val id: String,
    val name: String,
    val type: String,
    val thrusters: List<Thruster>,
    val trunk: Trunk,
    val diameter: LineSize,
    override val description: String?,
    @Transient override var descriptionRu: String? = null,
    override val wikipedia: String?,
    @Transient override var wikiLocale: String? = null,
    @Json(name = "active") val isActive: Boolean,
    @Json(name = "crew_capacity") val crewCapacity: Int = 0,
    @Json(name = "sidewall_angle_deg") val slideWallAngle: Int,
    @Json(name = "orbit_duration_yr") val orbitDuration: Int,
    @Json(name = "dry_mass_kg") val dryMass: Int,
    @Json(name = "first_flight") val firstFlight: Date?,
    @Json(name = "heat_shield") val heatShield: Shield,
    @Json(name = "launch_payload_mass") val launchPayloadMass: Mass,
    @Json(name = "launch_payload_vol") val launchPayloadVolume: Volume,
    @Json(name = "return_payload_mass") val returnPayloadMass: Mass,
    @Json(name = "return_payload_vol") val returnPayloadVolume: Volume,
    @Json(name = "pressurized_capsule") val pressurizedCapsule: PressurizedCapsule,
    @Json(name = "height_w_trunk") val heightWithTrunk: LineSize,
    @Json(name = "flickr_images") val images: List<String>,
) : HasStringId, HasDescription, HasWiki {

    fun toRoom() = DragonLocal(id, name, type, thrusters, trunk, diameter, description, descriptionRu, wikipedia, wikiLocale,
        isActive, crewCapacity, slideWallAngle, orbitDuration, dryMass, firstFlight, heatShield,
        launchPayloadMass, launchPayloadVolume, returnPayloadMass, returnPayloadVolume, pressurizedCapsule,
        heightWithTrunk, images)
}