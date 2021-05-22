package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import ru.alexmaryin.spacextimes_rx.data.model.extra.LineSize
import ru.alexmaryin.spacextimes_rx.data.model.extra.Mass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Volume
import ru.alexmaryin.spacextimes_rx.data.model.parts.PressurizedCapsule
import ru.alexmaryin.spacextimes_rx.data.model.parts.Shield
import ru.alexmaryin.spacextimes_rx.data.model.parts.Thruster
import ru.alexmaryin.spacextimes_rx.data.model.parts.Trunk
import java.util.*

@Entity(tableName = "dragons_table")
data class DragonLocal(
    @PrimaryKey val dragonId: String,
    val name: String,
    val type: String,
    val thrusters: List<Thruster>,
    @Embedded(prefix = "trunk") val trunk: Trunk,
    @Embedded(prefix = "diameter") val diameter: LineSize,
    val description: String?,
    val descriptionRu: String?,
    val wikipedia: String?,
    val wikiLocale: String?,
    val isActive: Boolean,
    val crewCapacity: Int,
    val slideWallAngle: Int,
    val orbitDuration: Int,
    val dryMass: Int,
    val firstFlight: Date?,
    @Embedded(prefix = "shield") val heatShield: Shield,
    @Embedded(prefix = "launchMass") val launchPayloadMass: Mass,
    @Embedded(prefix = "launchVolume") val launchPayloadVolume: Volume,
    @Embedded(prefix = "returnMass") val returnPayloadMass: Mass,
    @Embedded(prefix = "returnVolume") val returnPayloadVolume: Volume,
    @Embedded(prefix = "capsule") val pressurizedCapsule: PressurizedCapsule,
    @Embedded(prefix = "fullHeight") val heightWithTrunk: LineSize,
    val images: List<String>,
) {
    fun toResponse() = Dragon(dragonId, name, type, thrusters, trunk, diameter, description, descriptionRu,
        wikipedia, wikiLocale, isActive, crewCapacity, slideWallAngle, orbitDuration, dryMass,
        firstFlight, heatShield, launchPayloadMass, launchPayloadVolume, returnPayloadMass, returnPayloadVolume,
        pressurizedCapsule, heightWithTrunk, images)
}
