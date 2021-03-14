package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Dragon(
    val id: String,
    val name: String,
    val type: String,
    val thrusters: List<Thruster>,
    val trunk: Trunk,
    val diameter: LineSize,
    val wikipedia: String,
    val description: String?,
    var descriptionRu: String?,
    @SerializedName("active") val isActive: Boolean,
    @SerializedName("crew_capacity") val crewCapacity: Int,
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
)

data class Shield(
    val material: String,
    @SerializedName("size_meters") val size: Float,
    @SerializedName("temp_degrees") val temperature: Int,
    @SerializedName("dev_partner") val developPartner: String
)

data class PressurizedCapsule(
    @SerializedName("payload_volume") val payload: Volume
)

data class Trunk(
    @SerializedName("trunk_volume") val volume: Volume,
    val cargo: Cargo
)

data class Cargo(
    @SerializedName("solar_array") val solarArray: Int,
    @SerializedName("unpressurized_cargo") val unpressurized: Boolean
)