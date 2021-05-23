package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.parts.CoreFlight

@Entity(tableName = "core_flights_table")
data class CoreFlightWithoutDetails(
    @PrimaryKey val coreFlightId: String,
    val coreId: String,
    val flight: Int?,
    val gridfins: Boolean?,
    val legs: Boolean?,
    val reused: Boolean?,
    val landpad: String?,
    val landingAttempt: Boolean?,
    val landingSuccess: Boolean?,
    val landingType: String?,
) {
    fun toResponse() = CoreFlight(null, flight, gridfins, legs, reused, landpad, landingAttempt, landingSuccess, landingType)
}
