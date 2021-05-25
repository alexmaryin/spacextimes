package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasTitle
import ru.alexmaryin.spacextimes_rx.data.model.extra.HistoryLinks
import ru.alexmaryin.spacextimes_rx.data.room_model.HistoryLocal
import java.util.*

@JsonClass(generateAdapter = true)
data class History(
    override val id: String,
    override val title: String,
    override val details: String,
    val links: HistoryLinks?,
    @Json(name = "event_date_utc") val eventDateUTC: Date,
    @Json(name = "event_date_unix") val eventDateUnix: Long?,
    @Transient override var titleRu: String? = null,
    @Transient override var detailsRu: String? = null,
) : HasStringId, HasDetails, HasTitle {

    fun toRoom() = HistoryLocal(id, title, details, links, eventDateUTC, eventDateUnix)
}