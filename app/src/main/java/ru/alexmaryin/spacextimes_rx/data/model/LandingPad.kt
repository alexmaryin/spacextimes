package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches

@JsonClass(generateAdapter = true)
data class LandingPad(
    override val id: String,
    val name: String?,
    val type: LandingPadType,
    override val details: String?,
    @Transient override var detailsRu: String? = null,
    val status: PadStatus?,
    val launches: List<Launches> = emptyList(),
    @Json(name = "landing_attempts") val landingAttempts: Int = 0,
    @Json(name = "landing_successes") val landingSuccesses: Int = 0,
) : HasStringId, HasDetails
