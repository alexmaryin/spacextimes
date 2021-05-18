package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.lists.Capsules

@JsonClass(generateAdapter = true)
data class PayloadDragon(
    val capsule: Capsules? = null,
    val manifest: String? = null,
    @Json(name = "mass_returned_kg") val returnedMassInKg: Float? = null,
    @Json(name = "mass_returned_lbs") val returnedMassInLbs: Float? = null,
    @Json(name = "flight_time_sec") val flightTime: Int? = null,
    @Json(name = "water_landing") val waterLanding: Boolean? = null,
    @Json(name = "land_landing") val groundLanding: Boolean? = null,
) {
    fun isNotEmpty(): Boolean = this != PayloadDragon()
}