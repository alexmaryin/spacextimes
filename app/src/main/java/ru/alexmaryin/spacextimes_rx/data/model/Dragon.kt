package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
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
import java.util.*

data class Dragon(
    override val id: String,
    val name: String,
    val type: String,
    val thrusters: List<Thruster>,
    val trunk: Trunk,
    val diameter: LineSize,
    override val description: String?,
    override var descriptionRu: String?,
    override val wikipedia: String?,
    override var wikiLocale: String?,
    @SerializedName("active") val isActive: Boolean,
    @SerializedName("crew_capacity") val crewCapacity: Int = 0,
    @SerializedName("sidewall_angle_deg") val slideWallAngle: Int,
    @SerializedName("orbit_duration_yr") val orbitDuration: Int,
    @SerializedName("dry_mass_kg") val dryMass: Int,
    @SerializedName("first_flight") val firstFlight: Date,
    @SerializedName("heat_shield") val heatShield: Shield,
    @SerializedName("launch_payload_mass") val launchPayloadMass: Mass,
    @SerializedName("launch_payload_vol") val launchPayloadVolume: Volume,
    @SerializedName("return_payload_mass") val returnPayloadMass: Mass,
    @SerializedName("return_payload_vol") val returnPayloadVolume: Volume,
    @SerializedName("pressurized_capsule") val pressurizedCapsule: PressurizedCapsule,
    @SerializedName("height_w_trunk") val heightWithTrunk: LineSize,
    @SerializedName("flickr_images") val images: List<String>,
) : HasStringId, HasDescription, HasWiki