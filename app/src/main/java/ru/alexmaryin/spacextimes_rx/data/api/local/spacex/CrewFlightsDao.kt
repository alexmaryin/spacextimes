package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.CrewFlightLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.CrewFlightWithoutDetails

interface CrewFlightsDao {

    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrewFlights(crewFlight: List<CrewFlightWithoutDetails>)

    @Transaction
    @Query("select * from crew_flight_table where crewId=:crewId and launchId=:launchId")
    suspend fun selectCrewFlight(crewId: String, launchId: String): CrewFlightLocal?
}