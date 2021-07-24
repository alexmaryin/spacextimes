package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.parts.CrewFlight

@Entity(tableName = "crew_flight_table", primaryKeys = ["crewId", "launchId"])
data class CrewFlightWithoutDetails(
    @ColumnInfo(index = true) val crewId: String,
    @ColumnInfo(index = true) val launchId: String,
    val role: String,
) {
    fun toResponse(crew: Crew) = CrewFlight(crew, role)
}