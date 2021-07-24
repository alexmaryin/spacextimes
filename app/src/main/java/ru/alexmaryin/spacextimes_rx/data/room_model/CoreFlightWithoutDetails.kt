package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.parts.CoreFlight

@Entity(tableName = "core_flights_table", primaryKeys = ["coreId", "launchId"])
data class CoreFlightWithoutDetails(
    @ColumnInfo(index = true) val coreId: String,
    @ColumnInfo(index = true) val launchId: String,
    val flight: Int?,
    val gridfins: Boolean?,
    val legs: Boolean?,
    val reused: Boolean?,
    val landpad: String?,
    val landingAttempt: Boolean?,
    val landingSuccess: Boolean?,
    val landingType: String?,
) {
    fun toResponse(core: Core) = CoreFlight(core, flight, gridfins, legs, reused, landpad, landingAttempt, landingSuccess, landingType)
}
