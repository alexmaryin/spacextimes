package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.dimensions
import ru.alexmaryin.spacextimes_rx.data.model.Thruster
import java.util.*

data class Dragon(
    val name: String;
    val type: String;
    @SerializedName("active") val isActive: Boolean;
    @SerializedName("crew_capacity") val crewCapacity: Int;
    @SerializedName("sidewall_angle_deg") val slidewallAngle: Int;
    @SerializedName("orbit_duration_yr") val orbitDuration: Int;
    @SerializedName("dry_mass_kg") val dryMass: Int;
    @SerializedName("first_flight") val firstFlight: Date;
    @SerializedName("heat_shield") val geatShield: Shield;
    val thrusters: List<Thruster>;
    @SerializedName("launch_payload_mass") val launchPayloadMass: Mass;
    @SerializedName("launch_payload_vol") val launchPayloadVolume: Volume;
    @SerializedName("return_payload_mass") val returnPayloadMass: Mass;
    @SerializedName("return_payload_vol") val returnPayloadVolume: Volume;
    @SerializedName("pressurized_capsule") val pressurizedCapsule: PressurizedCapsule;
    val trunk: Trunk;
    @Serializedname("height_w_trunk") val heightWithTrunk: LineSize;
    val diameter: LineSize;
    @SerializedName("flickr_images") val images: List<String>;
    val wikipedia: String;
    val description: String;
)

data class Shield(
    val material: String;
    @SerializedName("size_meters") val size: Int;
    @SerializedName("temp_degrees") val temperature: Int;
    @SerializedName("dev_partner") val devPartner: String
)

data class  PressurizedCapsule(
    @SerializedName("payload_volume") val payload: Volume;
)

data class  Trunk(
    @Serializedname("trunk_volume") val volume: Volume;
    val cargo: object {
        @SerializedName("solar_array") val solarArray: Int;
        @SerializedName("unpressurized_cargo") val unpressurized: Boolean;
    }
)