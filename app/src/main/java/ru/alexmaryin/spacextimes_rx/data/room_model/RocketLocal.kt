package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Rocket
import ru.alexmaryin.spacextimes_rx.data.model.enums.RocketType
import ru.alexmaryin.spacextimes_rx.data.model.extra.LineSize
import ru.alexmaryin.spacextimes_rx.data.model.extra.Mass
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadWeight
import ru.alexmaryin.spacextimes_rx.data.model.parts.Engine
import ru.alexmaryin.spacextimes_rx.data.model.parts.FirstStage
import ru.alexmaryin.spacextimes_rx.data.model.parts.LandingLegs
import ru.alexmaryin.spacextimes_rx.data.model.parts.SecondStage
import java.util.*

@Entity(tableName = "rockets_table")
data class RocketLocal(
    @PrimaryKey val id: String,
    val name: String,
    val type: RocketType,
    val active: Boolean,
    val stages: Int,
    val boosters: Int,
    val country: String,
    val company: String,
    val wikipedia: String?,
    val wikiLocale: String?,
    val description: String?,
    val descriptionRu: String?,
    @Embedded val height: LineSize,
    @Embedded val diameter: LineSize,
    @Embedded val mass: Mass,
    @Embedded val engines: Engine,
    val successRate: Float,
    val costPerLaunch: Float,
    val firstFlight: Date,
    @Embedded val firstStage: FirstStage,
    @Embedded val secondStage: SecondStage,
    @Embedded val landingLegs: LandingLegs,
    val payloadWeights: List<PayloadWeight>,
    val images: List<String>,
) {
    fun toResponse() = Rocket(id, name, type, active, stages, boosters, country, company, wikipedia, wikiLocale,
        description, descriptionRu, height, diameter, mass, engines, successRate, costPerLaunch, firstFlight, firstStage,
        secondStage, landingLegs, payloadWeights, images)
}
