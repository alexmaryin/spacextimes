package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
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
import java.util.*

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
    override var wikiLocale: String?,
    override val description: String?,
    override var descriptionRu: String?,
    val height: LineSize,
    val diameter: LineSize,
    val mass: Mass,
    val engines: Engine,
    @SerializedName("success_rate_pct") val successRate: Float,
    @SerializedName("cost_per_launch") val costPerLaunch: Float,
    @SerializedName("first_flight") val firstFlight: Date,
    @SerializedName("first_stage") val firstStage: FirstStage,
    @SerializedName("second_stage") val secondStage: SecondStage,
    @SerializedName("landing_legs") val landingLegs: LandingLegs?,
    @SerializedName("payload_weights") val payloadWeights: List<PayloadWeight> = emptyList(),
    @SerializedName("flickr_images") val images: List<String> = emptyList(),
) : HasStringId, HasDescription, HasWiki