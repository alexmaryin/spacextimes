package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchPadLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchPadWithoutLaunches

interface LaunchPadDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchPads(pads: List<LaunchPadWithoutLaunches>)

    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchPad(pad: LaunchPadWithoutLaunches)

    @Transaction @Query("select * from launch_pads_table order by name")
    suspend fun selectAllLaunchPads(): List<LaunchPadLocal>

    @Transaction @Query("select * from launch_pads_table where launchPadId=:id")
    suspend fun selectLaunchPad(id: String): LaunchPadLocal?

    @Query("delete from launch_pads_table")
    suspend fun clearLaunchPads()
}