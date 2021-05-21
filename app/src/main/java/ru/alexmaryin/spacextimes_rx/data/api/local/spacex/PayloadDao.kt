package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmaryin.spacextimes_rx.data.room_model.PayloadLocal

interface PayloadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayload(payload: PayloadLocal)

    @Query("select * from payloads_table join payload_dragons_table on payload_dragons_table.id=id")
    suspend fun selectAllPayloads(): List<PayloadLocal>

    @Query("select * from payloads_table join payload_dragons_table on payload_dragons_table.id=id where id=:id ")
    suspend fun selectPayload(id: String): PayloadLocal?

    @Query("delete from payloads_table")
    suspend fun clearPayloads()
}