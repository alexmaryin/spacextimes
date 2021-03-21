package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus

data class LaunchPad(
    val id: String,
    val name: String,
    val locality: String,
    val region: String,
    val latitude: Float,
    val longitude: Float,
    val rockets: List<String> = emptyList(),
    val launches: List<String> = emptyList(),
    override val details: String?,
    override var detailsRu: String?,
    val status: PadStatus,
    @SerializedName("timezone") val timeZone: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("launch_attempts") val launchAttempts: Int = 0,
    @SerializedName("launch_successes") val launchSuccesses: Int = 0,
) : HasDetails