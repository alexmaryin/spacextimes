package ru.alexmaryin.spacextimes_rx.data.model

import android.content.Context
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.enums.RocketType
import ru.alexmaryin.spacextimes_rx.data.model.extra.LineSize
import ru.alexmaryin.spacextimes_rx.data.model.extra.Mass
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadWeight
import ru.alexmaryin.spacextimes_rx.data.model.parts.Engine
import ru.alexmaryin.spacextimes_rx.data.model.parts.FirstStage
import ru.alexmaryin.spacextimes_rx.data.model.parts.LandingLegs
import ru.alexmaryin.spacextimes_rx.data.model.parts.SecondStage
import ru.alexmaryin.spacextimes_rx.data.room_model.RocketLocal
import ru.alexmaryin.spacextimes_rx.utils.currentLocale
import java.text.DateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class Rocket(
    override val id: String,
    val name: String,
    val type: RocketType,
    val active: Boolean,
    val stages: Int,
    val boosters: Int,
    val country: String,
    val company: String,
    override val wikipedia: String?,
    @Transient override var wikiLocale: String? = null,
    override val description: String?,
    @Transient override var descriptionRu: String? = null,
    val height: LineSize,
    val diameter: LineSize,
    val mass: Mass,
    val engines: Engine,
    @Json(name = "success_rate_pct") val successRate: Float,
    @Json(name = "cost_per_launch") val costPerLaunch: Float,
    @Json(name = "first_flight") val firstFlight: Date,
    @Json(name = "first_stage") val firstStage: FirstStage,
    @Json(name = "second_stage") val secondStage: SecondStage,
    @Json(name = "landing_legs") val landingLegs: LandingLegs,
    @Json(name = "payload_weights") val payloadWeights: List<PayloadWeight> = emptyList(),
    @Json(name = "flickr_images") val images: List<String> = emptyList(),
) : HasStringId, HasDescription, HasWiki {

    fun firstFlightStr(context: Context): String =
        DateFormat.getDateInstance(DateFormat.LONG, context.currentLocale()).format(firstFlight)

    fun toRoom() = RocketLocal(id, name, type, active, stages, boosters, country, company, wikipedia, wikiLocale,
        description, descriptionRu, height, diameter, mass, engines, successRate, costPerLaunch, firstFlight, firstStage,
        secondStage, landingLegs, payloadWeights, images)
}