package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.CoreLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.CoreWithoutLaunches

interface CoresDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCores(cores: List<CoreWithoutLaunches>)

    @Transaction @Query("select * from cores_table")
    suspend fun selectAllCores(): List<CoreLocal>

    @Transaction @Query("select * from cores_table where coreId=:id")
    suspend fun selectCore(id: String): CoreLocal?

    @Transaction @Query("delete from cores_table")
    suspend fun clearCores()
}