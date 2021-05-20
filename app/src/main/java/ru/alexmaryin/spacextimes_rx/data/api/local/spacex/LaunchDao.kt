package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchLocal

interface LaunchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunch(launch: LaunchLocal)

    @Query("select * from launches_table")
    suspend fun selectAllLaunches(): List<LaunchLocal>

    @Query("select * from launches_table where id=:id")
    suspend fun selectLaunch(id: String): LaunchLocal?

    @Query("delete from launches_table")
    suspend fun clearLaunches()
}