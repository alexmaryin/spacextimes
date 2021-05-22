package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchPadLocal

interface LaunchPadDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchPads(pads: List<LaunchPadLocal>)

    @Transaction @Query("select * from launch_pads_table")
    suspend fun selectAllLaunchPads(): List<LaunchPadLocal>

    @Transaction @Query("select * from launch_pads_table where launchPadId=:id")
    suspend fun selectLaunchPad(id: String): LaunchPadLocal?

    @Transaction @Query("delete from launch_pads_table")
    suspend fun clearLaunchPads()
}