package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus

data class LandingPad(
    override val id: String,
    val name: String,
    val type: LandingPadType,
    val locality: String,
    val region: String,
    val latitude: Float?,
    val longitude: Float?,
    override val wikipedia: String?,
    override var wikiLocale: String?,
    override val details: String?,
    override var detailsRu: String? = null,
    val status: PadStatus,
    val launches: List<String> = emptyList(),
    @SerializedName("full_name") val fullName: String,
    @SerializedName("landing_attempts") val landingAttempts: Int = 0,
    @SerializedName("landing_successes") val landingSuccesses: Int = 0,
) : HasStringId, HasDetails, HasWiki
