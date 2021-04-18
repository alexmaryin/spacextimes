package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.lists.Capsules

data class PayloadDragon(
    val capsule: Capsules? = null,
    val manifest: String? = null,
    @SerializedName("mass_returned_kg") val returnedMassInKg: Float? = null,
    @SerializedName("mass_returned_lbs") val returnedMassInLbs: Float? = null,
    @SerializedName("flight_time_sec") val flightTime: Int? = null,
    @SerializedName("water_landing") val waterLanding: Boolean? = null,
    @SerializedName("land_landing") val groundLanding: Boolean? = null,
) {
    fun isNotEmpty(): Boolean = this != PayloadDragon()
}