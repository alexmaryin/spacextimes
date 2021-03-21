package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus

data class LandingPad(
    val id: String,
    val name: String,
    val type: LandingPadType,
    val locality: String,
    val region: String,
    val latitude: Float?,
    val longitude: Float?,
    val wikipedia: String?,
    var wikiLocale: String?,
    val details: String?,
    var detailsRu: String? = null,
    val status: PadStatus,
    val launches: List<String> = emptyList(),
    @SerializedName("full_name") val fullName: String,
    @SerializedName("landing_attempts") val landingAttempts: Int = 0,
    @SerializedName("landing_successes") val landingSuccesses: Int = 0,
)
