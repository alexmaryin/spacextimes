package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.LaunchPad
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.extra.Images

@Entity(tableName = "launch_pads_table")
data class LaunchPadWithoutLaunches(
    @PrimaryKey val launchPadId: String,
    val name: String?,
    val locality: String?,
    val region: String,
    val latitude: Float,
    val longitude: Float,
    val status: PadStatus,
    val details: String?,
    val timeZone: String,
    val fullName: String?,
    val launchAttempts: Int,
    val launchSuccesses: Int,
    @Embedded(prefix = "images") val images: Images,
) {
    fun toResponse() = LaunchPad(launchPadId, name, locality, region, latitude, longitude, status, details, timeZone, fullName,
        launchAttempts, launchSuccesses, images)
}
