package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.CoreFlightLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.CoreFlightWithoutDetails

interface CoreFlightsDao {

    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoreFlights(coreFlights: List<CoreFlightWithoutDetails>)

    @Transaction
    @Query("select * from core_flights_table where coreId=:coreId and launchId=:launchId")
    suspend fun selectCoreFlight(coreId: String, launchId: String): CoreFlightLocal?

    @Query("delete from core_flights_table")
    suspend fun clearCoreFlights()
}
