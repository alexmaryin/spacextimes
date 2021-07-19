package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.parts.CoreFlight

@Entity(tableName = "core_flights_table")
data class CoreFlightWithoutDetails(
    val coreId: String,
    val flight: Int?,
    val gridfins: Boolean?,
    val legs: Boolean?,
    val reused: Boolean?,
    val landpad: String?,
    val landingAttempt: Boolean?,
    val landingSuccess: Boolean?,
    val landingType: String?,
    @PrimaryKey(autoGenerate = true) val coreFlightId: Int = -1
) {
    fun toResponse(core: Core) = CoreFlight(core, flight, gridfins, legs, reused, landpad, landingAttempt, landingSuccess, landingType)
}
