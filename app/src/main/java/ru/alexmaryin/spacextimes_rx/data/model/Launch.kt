package ru.alexmaryin.spacextimes_rx.data.model

import android.content.Context
import com.google.android.material.timepicker.TimeFormat
import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.data.model.extra.Failure
import ru.alexmaryin.spacextimes_rx.data.model.extra.Links
import ru.alexmaryin.spacextimes_rx.data.model.parts.CoreFlight
import ru.alexmaryin.spacextimes_rx.data.model.parts.Fairings
import ru.alexmaryin.spacextimes_rx.utils.currentLocale
import ru.alexmaryin.spacextimes_rx.utils.halfYearString
import ru.alexmaryin.spacextimes_rx.utils.quarterYearString
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class Launch(
    override val id: String,
    val name: String,
    val window: Int?,
//    val rocket: String?,
    val rocket: Rocket,
    val success: Boolean,
    val upcoming: Boolean,
    override val details: String?,
    override var detailsRu: String?,
    val fairings: Fairings,
    val links: Links,
    val crew: List<String> = emptyList(),
    val ships: List<String> = emptyList(),
    val capsules: List<String> = emptyList(),
    val payloads: List<String> = emptyList(),
    val cores: List<CoreFlight> = emptyList(),
    val failures: List<Failure> = emptyList(),
    @SerializedName("launchpad") val launchPad: String?,
    @SerializedName("auto_update") val autoUpdate: Boolean,
    @SerializedName("flight_number") val flightNumber: Int,
    @SerializedName("date_utc") val dateUtc: Date,
    @SerializedName("dater_unix") val dateUnix: Long,
    @SerializedName("date_local") val dateLocal: Date,
    @SerializedName("date_precision") val datePrecision: DatePrecision,
    @SerializedName("static_fire_date_utc") val staticFireDateUtc: Date?,
    @SerializedName("static_fire_date_unix") val staticFireDateUnix: Long?,
    @SerializedName("tbd") val toBeDetermined: Boolean = false,
    @SerializedName("net") val notEarlyThan: Boolean = false,
) : HasStringId, HasDetails {
    fun dateTrimmed(context: Context): String = when(datePrecision) {
        DatePrecision.YEAR_HALF -> halfYearString(context, dateLocal)
        DatePrecision.YEAR_QUARTER -> quarterYearString(context, dateLocal)
        DatePrecision.YEAR -> SimpleDateFormat("yyyy", context.currentLocale()).format(dateLocal)
        DatePrecision.MONTH -> SimpleDateFormat("MMM yyyy", context.currentLocale()).format(dateLocal)
        DatePrecision.DAY -> DateFormat.getDateInstance(DateFormat.LONG).format(dateLocal)
        DatePrecision.HOUR -> DateFormat.getDateTimeInstance(DateFormat.LONG, TimeFormat.CLOCK_24H).format(dateLocal)
    }
}