package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.LaunchPad
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus

@Entity(tableName = "launch_pads_table")
data class LaunchPadLocal(
    @PrimaryKey val id: String,
    val name: String?,
    val locality: String?,
    val region: String,
    val latitude: Float,
    val longitude: Float,
    val status: PadStatus,
    val details: String?,
    val detailsRu: String? = null,
    val timeZone: String,
    val fullName: String?,
    val launchAttempts: Int = 0,
    val launchSuccesses: Int = 0,
) {
    fun toResponse() = LaunchPad(id, name, locality, region, latitude, longitude, status, details, detailsRu, timeZone, fullName)
}
