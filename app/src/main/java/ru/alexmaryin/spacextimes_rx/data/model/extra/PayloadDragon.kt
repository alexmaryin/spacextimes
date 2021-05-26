package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.room_model.PayloadDragonWithoutCapsule

@JsonClass(generateAdapter = true)
data class PayloadDragon(
    var capsule: Capsule? = null,
    val manifest: String? = null,
    @Json(name = "mass_returned_kg") val returnedMassInKg: Float? = null,
    @Json(name = "mass_returned_lbs") val returnedMassInLbs: Float? = null,
    @Json(name = "flight_time_sec") val flightTime: Int? = null,
    @Json(name = "water_landing") val waterLanding: Boolean? = null,
    @Json(name = "land_landing") val groundLanding: Boolean? = null,
) {
    fun isNotEmpty() = this != PayloadDragon()

    fun isEmpty() = this == PayloadDragon()

    fun toRoom(payloadId: String) = PayloadDragonWithoutCapsule(payloadId, capsule?.id, manifest, returnedMassInKg, returnedMassInLbs,
        flightTime, waterLanding, groundLanding)
}