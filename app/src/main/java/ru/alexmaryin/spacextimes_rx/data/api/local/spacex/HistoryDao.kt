package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmaryin.spacextimes_rx.data.room_model.HistoryLocal

interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryEvent(event: HistoryLocal)

    @Query("select * from history_events_table")
    suspend fun selectAllHistoryEvents(): List<HistoryLocal>

    @Query("select * from history_events_table where id=:id")
    suspend fun selectHistoryEvent(id: String): HistoryLocal?

    @Query("delete from history_events_table")
    suspend fun clearHistory()
}