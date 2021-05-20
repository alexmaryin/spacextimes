package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchPadLocal

interface LaunchPadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchPad(pad: LaunchPadLocal)

    @Query("select * from launch_pads_table")
    suspend fun selectAllLaunchPads(): List<LaunchPadLocal>

    @Query("select * from launch_pads_table where id=:id")
    suspend fun selectLaunchPad(id: String): LaunchPadLocal?

    @Query("delete from launch_pads_table")
    suspend fun clearLaunchPads()
}