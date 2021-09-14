package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.CrewLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.CrewWithoutLaunches

interface CrewDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrew(crew: List<CrewWithoutLaunches>)

    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(crew: CrewWithoutLaunches)

    @Transaction @Query("select * from crew_table order by crewId")
    suspend fun selectAllCrew(): List<CrewLocal>

    @Transaction @Query("select * from crew_table where crewId=:id")
    suspend fun selectCrewMember(id: String): CrewLocal?

    @Query("delete from crew_table")
    suspend fun clearCrew()
}