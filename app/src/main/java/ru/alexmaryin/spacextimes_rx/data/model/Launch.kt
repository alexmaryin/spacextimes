package ru.alexmaryin.spacextimes_rx.data.model

import android.content.Context
import com.google.android.material.timepicker.TimeFormat
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.data.model.extra.Failure
import ru.alexmaryin.spacextimes_rx.data.model.extra.Links
import ru.alexmaryin.spacextimes_rx.data.model.parts.CoreFlight
import ru.alexmaryin.spacextimes_rx.data.model.parts.Fairings
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchLocal
import ru.alexmaryin.spacextimes_rx.utils.currentLocale
import ru.alexmaryin.spacextimes_rx.utils.halfYearString
import ru.alexmaryin.spacextimes_rx.utils.quarterYearString
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class Launch(
    override val id: String,
    val name: String,
    val window: Int?,
    val rocket: Rocket?,
    val success: Boolean? = null,
    val upcoming: Boolean,
    override val details: String?,
    @Transient override var detailsRu: String? = null,
    val fairings: Fairings?,
    val links: Links,
    @Json(name = "auto_update") val autoUpdate: Boolean,
    @Json(name = "flight_number") val flightNumber: Int,
    @Json(name = "date_utc") val dateUtc: Date,
    @Json(name = "dater_unix") val dateUnix: Long?,
    @Json(name = "date_local") val dateLocal: Date,
    @Json(name = "date_precision") val datePrecision: DatePrecision,
    @Json(name = "static_fire_date_utc") val staticFireDateUtc: Date?,
    @Json(name = "static_fire_date_unix") val staticFireDateUnix: Long?,
    @Json(name = "tbd") val toBeDetermined: Boolean = false,
    @Json(name = "net") val notEarlyThan: Boolean = false,
    @Json(name = "launchpad") val launchPad: LaunchPad? = null,
    val crew: List<Crew> = emptyList(),
    val capsules: List<Capsule> = emptyList(),
    val payloads: List<Payload> = emptyList(),
    val cores: List<CoreFlight> = emptyList(),
    val failures: List<Failure> = emptyList(),
) : HasStringId, HasDetails, HasWiki {

    override val wikipedia get() = links.wikipedia
    override var wikiLocale
        get() = links.wikiLocale
        set(value) { links.wikiLocale = value }

    val images get() = with(links) { flickr.original.ifEmpty { flickr.small } }

    fun dateTrimmed(context: Context): String = when(datePrecision) {
        DatePrecision.YEAR_HALF -> halfYearString(context, dateLocal)
        DatePrecision.YEAR_QUARTER -> quarterYearString(context, dateLocal)
        DatePrecision.YEAR -> SimpleDateFormat("yyyy", context.currentLocale()).format(dateLocal)
        DatePrecision.MONTH -> SimpleDateFormat("MMM yyyy", context.currentLocale()).format(dateLocal)
        DatePrecision.DAY -> DateFormat.getDateInstance(DateFormat.LONG).format(dateLocal)
        DatePrecision.HOUR -> DateFormat.getDateTimeInstance(DateFormat.LONG, TimeFormat.CLOCK_24H).format(dateLocal)
    }

    fun toRoom() = LaunchLocal(id, name, window, rocket, success, upcoming, details, detailsRu, fairings, links,
        autoUpdate, flightNumber, dateUtc, dateUnix, dateLocal, datePrecision, staticFireDateUtc, staticFireDateUnix,
        toBeDetermined, notEarlyThan)
}