package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.enums.CrewStatus

@Entity(tableName = "crew_table")
data class CrewLocal(
    @PrimaryKey val id: String,
    val name: String?,
    val status: CrewStatus,
    val agency: String?,
    val image: String?,
    val wikipedia: String?,
) {

    fun toResponse() = Crew(id, name, status, agency, image, wikipedia)
}