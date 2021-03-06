package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.room_model.CoreFlightLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.CoreFlightWithoutDetails

@JsonClass(generateAdapter = true)
data class CoreFlight(
    var core: Core? = null,
    val flight: Int? = null,
    val gridfins: Boolean? = null,
    val legs: Boolean? = null,
    val reused: Boolean? = null,
    val landpad: String? = null,
    @Json(name = "landing_attempt") val landingAttempt: Boolean? = null,
    @Json(name = "landing_success") val landingSuccess: Boolean? = null,
    @Json(name = "landing_type") val landingType: String? = null,
) {
    val isNotEmpty get() = this != CoreFlight()

    fun toRoom() = core?.let {
        if (isNotEmpty) CoreFlightLocal(CoreFlightWithoutDetails(it.id, it.id, flight, gridfins, legs, reused, landpad, landingAttempt,
            landingSuccess, landingType), it.toRoom(), emptyList()) else null
    }
}