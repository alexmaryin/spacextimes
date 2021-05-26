package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.PayloadLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.PayloadWithoutDragon

interface PayloadDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayloads(payloads: List<PayloadWithoutDragon>)

    @Transaction @Query("select * from payloads_table")
    suspend fun selectAllPayloads(): List<PayloadLocal>

    @Transaction @Query("select * from payloads_table where payloadId=:id")
    suspend fun selectPayload(id: String): PayloadLocal?

    @Transaction @Query("delete from payloads_table")
    suspend fun clearPayloads()
}