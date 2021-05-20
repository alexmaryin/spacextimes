package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.data.model.Rocket
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.data.model.extra.Links
import ru.alexmaryin.spacextimes_rx.data.model.parts.Fairings
import java.util.*

@Entity(tableName = "launches_table")
data class LaunchLocal(
    @PrimaryKey val id: String,
    val name: String,
    val window: Int?,
    val rocket: Rocket?,
    val success: Boolean? = null,
    val upcoming: Boolean,
    val details: String?,
    val detailsRu: String? = null,
    val fairings: Fairings?,
    val links: Links,
    val autoUpdate: Boolean,
    val flightNumber: Int,
    val dateUtc: Date,
    val dateUnix: Long?,
    val dateLocal: Date,
    val datePrecision: DatePrecision,
    val staticFireDateUtc: Date?,
    val staticFireDateUnix: Long?,
    val toBeDetermined: Boolean = false,
    val notEarlyThan: Boolean = false,
) {
    fun toResponse() = Launch(id, name, window, rocket, success, upcoming, details, detailsRu, fairings, links,
        autoUpdate, flightNumber, dateUtc, dateUnix, dateLocal, datePrecision, staticFireDateUtc, staticFireDateUnix,
        toBeDetermined, notEarlyThan)
}
