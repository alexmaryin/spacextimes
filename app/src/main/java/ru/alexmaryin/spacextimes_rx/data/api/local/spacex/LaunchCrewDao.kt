package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchCrewWithoutDetails

interface LaunchCrewDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchCrew(crew: List<LaunchCrewWithoutDetails>)

    @Transaction
    @Query("select * from launch_crew_table where launchCrewId=:id")
    suspend fun selectLaunchCrew(id: String): LaunchCrewWithoutDetails?
}