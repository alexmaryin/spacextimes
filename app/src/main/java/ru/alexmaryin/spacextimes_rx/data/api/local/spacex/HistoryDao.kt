package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.HistoryLocal

interface HistoryDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryEvents(events: List<HistoryLocal>)

    @Transaction @Query("select * from history_events_table order by eventDateUnix")
    suspend fun selectAllHistoryEvents(): List<HistoryLocal>

    @Transaction @Query("select * from history_events_table where historyId=:id")
    suspend fun selectHistoryEvent(id: String): HistoryLocal?
}