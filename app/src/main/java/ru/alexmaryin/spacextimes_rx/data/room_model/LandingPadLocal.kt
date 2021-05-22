package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.LandingPad
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus

@Entity(tableName = "landing_pads_table")
data class LandingPadLocal(
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
    val detailsRu: String? = null,
    val landingAttempts: Int = 0,
    val landingSuccesses: Int = 0,
    val fullName: String?,
) {
    fun toResponse() = LandingPad(landingPadId, name, type, locality, region, latitude, longitude, status, emptyList(),
        wikipedia, null, details, detailsRu, landingAttempts, landingSuccesses, fullName)
}
