package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName

data class CoreFlight(
    val core: String,
    val flight: Int,
    val gridfins: Boolean,
    val legs: Boolean,
    val reused: Boolean,
    val landpad: String,
    @SerializedName("landing_attempt") val landingAttempt: Boolean,
    @SerializedName("landing_success") val landingSuccess: Boolean,
    @SerializedName("landing_type") val landingType: String?,
)