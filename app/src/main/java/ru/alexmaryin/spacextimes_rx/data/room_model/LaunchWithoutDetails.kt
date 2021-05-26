package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.data.model.extra.Failure
import ru.alexmaryin.spacextimes_rx.data.model.extra.Links
import ru.alexmaryin.spacextimes_rx.data.model.parts.Fairings
import java.util.*

@Entity(tableName = "launches_table")
data class LaunchWithoutDetails(
    @PrimaryKey val launchId: String,
    val refreshTime: Long?,
    val name: String,
    val rocketId: String?,
    val window: Int?,
    val success: Boolean?,
    val upcoming: Boolean,
    val details: String?,
    @Embedded val fairings: Fairings?,
    @Embedded val links: Links,
    val autoUpdate: Boolean,
    val flightNumber: Int,
    val dateUtc: Date,
    val dateUnix: Long,
    val dateLocal: Date,
    val datePrecision: DatePrecision,
    val staticFireDateUtc: Date?,
    val staticFireDateUnix: Long?,
    val toBeDetermined: Boolean,
    val notEarlyThan: Boolean,
    val launchPadId: String?,
    val failures: List<Failure>,
) {
    fun toResponse() = Launch(launchId, refreshTime, name, window, null, success, upcoming, details, fairings, links, autoUpdate, flightNumber,
        dateUtc, dateUnix, dateLocal, datePrecision, staticFireDateUtc, staticFireDateUnix, toBeDetermined, notEarlyThan, null,
        failures)
}
