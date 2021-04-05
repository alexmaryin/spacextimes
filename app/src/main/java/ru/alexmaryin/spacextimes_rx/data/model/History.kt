package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.extra.HistoryLinks
import java.util.*

data class History(
    override val id: String,
    val title: String,
    var titleRu: String?,
    override val details: String,
    override var detailsRu: String?,
    val links: HistoryLinks,
    @SerializedName("event_date_utc") val eventDateUTC: Date,
    @SerializedName("event_date_unix") val eventDateUnix: Long,
) : HasStringId, HasDetails