package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasTitle
import ru.alexmaryin.spacextimes_rx.data.model.extra.HistoryLinks
import java.util.*

@JsonClass(generateAdapter = true)
data class History(
    override val id: String,
    override val title: String,
    @Transient override var titleRu: String? = null,
    override val details: String,
    @Transient override var detailsRu: String? = null,
    val links: HistoryLinks,
    @Json(name = "event_date_utc") val eventDateUTC: Date,
    @Json(name = "event_date_unix") val eventDateUnix: Long?,
) : HasStringId, HasDetails, HasTitle