package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.parts.LaunchCrew

@Entity(tableName = "launch_crew_table")
data class LaunchCrewWithoutDetails(
    val crewId: String,
    val role: String,
    @PrimaryKey(autoGenerate = true) val launchCrewId: Int? = null
) {
    fun toResponse(crew: Crew) = LaunchCrew(crew, role)
}