package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.room_model.CoreFlightWithoutDetails

@JsonClass(generateAdapter = true)
data class CoreFlight(
    var core: Core?,
    val flight: Int?,
    val gridfins: Boolean?,
    val legs: Boolean?,
    val reused: Boolean?,
    val landpad: String?,
    @Json(name = "landing_attempt") val landingAttempt: Boolean?,
    @Json(name = "landing_success") val landingSuccess: Boolean?,
    @Json(name = "landing_type") val landingType: String?,
) {
    fun toRoom() = core?.let {
        CoreFlightWithoutDetails(it.id, it.id, flight, gridfins, legs, reused, landpad, landingAttempt, landingSuccess, landingType)
    } ?: throw RuntimeException("Core inside CoreFlight object is not initialised")
}