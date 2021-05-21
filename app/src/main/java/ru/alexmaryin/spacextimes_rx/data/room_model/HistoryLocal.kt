package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import ru.alexmaryin.spacextimes_rx.data.model.History
import ru.alexmaryin.spacextimes_rx.data.model.extra.HistoryLinks
import java.util.*

@Entity(tableName = "history_events_table")
data class HistoryLocal(
    @PrimaryKey val id: String,
    val title: String,
    val titleRu: String? = null,
    val details: String,
    val detailsRu: String? = null,
    @Embedded val links: HistoryLinks,
    val eventDateUTC: Date,
    val eventDateUnix: Long?,
) {
    fun toResponse() = History(id, title, titleRu, details, detailsRu, links, eventDateUTC, eventDateUnix)
}
