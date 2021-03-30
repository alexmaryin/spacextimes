package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.google.gson.annotations.SerializedName

data class PayloadDragon(
    val capsule: String?,
    val manifest: String?,
    @SerializedName("mass_returned_kg") val returnedMassInKg: Float?,
    @SerializedName("mass_returned_lbs") val returnedMassInLbs: Float?,
    @SerializedName("flight_time_sec") val flightTime: Int?,
    @SerializedName("water_landing") val waterLanding: Boolean?,
    @SerializedName("land_landing") val groundLanding: Boolean?,
)