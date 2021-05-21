package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmaryin.spacextimes_rx.data.room_model.RocketLocal

interface RocketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocket(rocket: RocketLocal)

    @Query("select * from rockets_table")
    suspend fun selectAllRockets(): List<RocketLocal>

    @Query("select * from rockets_table where id=:id")
    suspend fun selectRocket(id: String): RocketLocal?

    @Query("delete from rockets_table")
    suspend fun clearRockets()
}