package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus

data class LandingPad(
    val id: String,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val type: LandingPadType,
    val locality: String,
    val region: String,
    val latitude: Float,
    val longitude: Float,
    @SerializedName("landing_attempts") val landingAttempts: Int = 0,
    @SerializedName("landing_successes") val landingSuccesses: Int = 0,
    val wikipedia: String?,
    val details: String?,
    val launches: List<Launch> = emptyList(),
    val status: PadStatus,
)
