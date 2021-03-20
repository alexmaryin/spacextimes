package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus

data class LaunchPad(
    val id: String,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val locality: String,
    val region: String,
    @SerializedName("timezone") val timeZone: String,
    val latitude: Float,
    val longitude: Float,
    @SerializedName("launch_attempts") val launchAttempts: Int = 0,
    @SerializedName("launch_successes") val launchSuccesses: Int = 0,
    val rockets: List<Rocket> = emptyList(),
    val launches: List<Launch> = emptyList(),
    val details: String?,
    val status: PadStatus,
)