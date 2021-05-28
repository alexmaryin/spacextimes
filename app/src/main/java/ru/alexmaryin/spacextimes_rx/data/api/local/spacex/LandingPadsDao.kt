package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.LandingPadLocal

interface LandingPadsDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLandingPads(pads: List<LandingPadLocal>)

    @Transaction @Query("select * from landing_pads_table")
    suspend fun selectAllLandingPads(): List<LandingPadLocal>

    @Transaction @Query("select * from landing_pads_table where landingPadId=:id")
    suspend fun selectLandingPad(id: String): LandingPadLocal?
}