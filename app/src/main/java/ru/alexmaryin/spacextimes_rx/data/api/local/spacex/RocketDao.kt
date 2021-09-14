package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.RocketLocal

interface RocketDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRockets(rockets: List<RocketLocal>)

    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocket(rocket: RocketLocal)

    @Transaction @Query("select * from rockets_table order by name")
    suspend fun selectAllRockets(): List<RocketLocal>

    @Transaction @Query("select * from rockets_table where rocketId=:id")
    suspend fun selectRocket(id: String): RocketLocal?

    @Query("delete from rockets_table")
    suspend fun clearRockets()
}