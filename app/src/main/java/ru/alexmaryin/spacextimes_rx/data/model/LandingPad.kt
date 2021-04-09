package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches

data class LandingPad(
    override val id: String,
    val name: String,
    val type: LandingPadType,
    override val details: String?,
    override var detailsRu: String? = null,
    val status: PadStatus,
    val launches: List<Launches> = emptyList(),
    @SerializedName("landing_attempts") val landingAttempts: Int = 0,
    @SerializedName("landing_successes") val landingSuccesses: Int = 0,
) : HasStringId, HasDetails
