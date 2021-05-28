package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchLocalShort
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchWithoutDetails

interface LaunchDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<LaunchWithoutDetails>)

    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunch(launch: LaunchWithoutDetails)

    @Transaction @Query("select * from launches_table order by upcoming desc, flightNumber desc, name")
    suspend fun selectLaunchesForList(): List<LaunchLocalShort>

    @Transaction @Query("select * from launches_table where launchId=:id")
    suspend fun selectLaunch(id: String): LaunchLocal?
}