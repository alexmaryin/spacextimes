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
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchWithoutDetails
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
    var rocket: Rocket?,
    val success: Boolean?,
    val upcoming: Boolean,
    override val details: String?,
    @Transient override var detailsRu: String? = null,
    val fairings: Fairings?,
    val links: Links,
    @Json(name = "auto_update") val autoUpdate: Boolean,
    @Json(name = "flight_number") val flightNumber: Int,
    @Json(name = "date_utc") val dateUtc: Date,
    @Json(name = "date_unix") val dateUnix: Long,
    @Json(name = "date_local") val dateLocal: Date,
    @Json(name = "date_precision") val datePrecision: DatePrecision,
    @Json(name = "static_fire_date_utc") val staticFireDateUtc: Date?,
    @Json(name = "static_fire_date_unix") val staticFireDateUnix: Long?,
    @Json(name = "tbd") val toBeDetermined: Boolean = false,
    @Json(name = "net") val notEarlyThan: Boolean = false,
    @Json(name = "launchpad") var launchPad: LaunchPad?,
    var crew: List<Crew> = emptyList(),
    var capsules: List<Capsule> = emptyList(),
    var payloads: List<Payload> = emptyList(),
    var cores: List<CoreFlight> = emptyList(),
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

    fun toRoom() = LaunchLocal(
        launch = LaunchWithoutDetails(id, name, rocket?.id, window, success, upcoming, details, detailsRu, fairings, links,
            autoUpdate, flightNumber, dateUtc, dateUnix, dateLocal, datePrecision, staticFireDateUtc, staticFireDateUnix,
            toBeDetermined, notEarlyThan, launchPad?.id, failures),
        rocket = rocket?.toRoom(),
        launchPad = launchPad?.toRoom(),
        crew = crew.map { it.toRoom() },
        capsules = capsules.map { it.toRoom() },
        payloads = payloads.map { it.toRoom().payload },
        cores = cores.map { it.toRoom() }
    )
}