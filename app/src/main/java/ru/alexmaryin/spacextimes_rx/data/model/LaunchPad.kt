package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches

@JsonClass(generateAdapter = true)
data class LaunchPad(
    override val id: String,
    val name: String?,
    val rockets: List<Rocket> = emptyList(),
    val launches: List<Launches> = emptyList(),
    override val details: String?,
    @Transient override var detailsRu: String? = null,
    val status: PadStatus,
    @Json(name = "launch_attempts") val launchAttempts: Int = 0,
    @Json(name = "launch_successes") val launchSuccesses: Int = 0,
) : HasStringId, HasDetails