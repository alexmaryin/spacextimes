package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmaryin.spacextimes_rx.data.room_model.CoreLocal

interface CoresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCore(core: CoreLocal)

    @Query("select * from cores_table")
    suspend fun selectAllCores(): List<CoreLocal>

    @Query("select * from cores_table where id=:id")
    suspend fun selectCore(id: String): CoreLocal?

    @Query("delete from cores_table")
    suspend fun clearCores()
}