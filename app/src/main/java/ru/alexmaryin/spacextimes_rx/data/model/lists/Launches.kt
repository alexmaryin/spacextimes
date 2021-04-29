package ru.alexmaryin.spacextimes_rx.data.model.lists

import android.content.Context
import com.google.android.material.timepicker.TimeFormat
import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.Rocket
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.data.model.extra.Links
import ru.alexmaryin.spacextimes_rx.utils.currentLocale
import ru.alexmaryin.spacextimes_rx.utils.halfYearString
import ru.alexmaryin.spacextimes_rx.utils.quarterYearString
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class Launches(
    override val id: String,
    val name: String,
    val success: Boolean? = null,
    val upcoming: Boolean = true,
    val links: Links,
    val rocket: Rocket,
    override val details: String? = null,
    override var detailsRu: String? = null,
    @SerializedName("tbd") val toBeDetermined: Boolean = false,
    @SerializedName("net") val notEarlyThan: Boolean = false,
    @SerializedName("date_local") val dateLocal: Date,
    @SerializedName("date_precision") val datePrecision: DatePrecision,
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
