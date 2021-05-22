package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.History
import ru.alexmaryin.spacextimes_rx.data.model.extra.HistoryLinks
import java.util.*

@Entity(tableName = "history_events_table")
data class HistoryLocal(
    @PrimaryKey val historyId: String,
    val title: String,
    val titleRu: String?,
    val details: String,
    val detailsRu: String?,
    @Embedded val links: HistoryLinks?,
    val eventDateUTC: Date,
    val eventDateUnix: Long?,
) {
    fun toResponse() = History(historyId, title, titleRu, details, detailsRu, links, eventDateUTC, eventDateUnix)
}
