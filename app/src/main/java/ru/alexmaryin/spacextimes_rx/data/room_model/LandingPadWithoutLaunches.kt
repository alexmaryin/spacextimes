package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.LandingPad
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.extra.Images

@Entity(tableName = "landing_pads_table")
data class LandingPadWithoutLaunches(
    @PrimaryKey val landingPadId: String,
    val name: String?,
    val type: LandingPadType,
    val locality: String?,
    val region: String?,
    val latitude: Float?,
    val longitude: Float?,
    val status: PadStatus?,
    val wikipedia: String?,
    val details: String?,
    val landingAttempts: Int,
    val landingSuccesses: Int,
    val fullName: String?,
    @Embedded(prefix = "images") val images: Images,
) {
    fun toResponse() = LandingPad(
        landingPadId, name, type, locality, region, latitude, longitude, status, emptyList(),
        wikipedia, details, landingAttempts, landingSuccesses, fullName, images
    )
}
