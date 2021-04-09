package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches

data class LaunchPad(
    override val id: String,
    val name: String,
    val rockets: List<Rocket> = emptyList(),
    val launches: List<Launches> = emptyList(),
    override val details: String?,
    override var detailsRu: String?,
    val status: PadStatus,
    @SerializedName("launch_attempts") val launchAttempts: Int = 0,
    @SerializedName("launch_successes") val launchSuccesses: Int = 0,
) : HasStringId, HasDetails