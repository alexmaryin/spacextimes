package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmaryin.spacextimes_rx.data.room_model.CrewLocal

interface CrewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrew(crew: CrewLocal)

    @Query("select * from crew_table")
    suspend fun selectAllCrew(): List<CrewLocal>

    @Query("select * from crew_table where id=:id")
    suspend fun selectCrewMember(id: String): CrewLocal?

    @Query("delete from crew_table")
    suspend fun clearCrew()
}