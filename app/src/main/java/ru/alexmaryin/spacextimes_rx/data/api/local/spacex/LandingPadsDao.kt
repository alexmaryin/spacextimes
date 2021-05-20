package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmaryin.spacextimes_rx.data.room_model.LandingPadLocal

interface LandingPadsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLandingPad(pad: LandingPadLocal)

    @Query("select * from landing_pads_table")
    suspend fun selectAllLandingPads(): List<LandingPadLocal>

    @Query("select * from landing_pads_table where id=:id")
    suspend fun selectLandingPad(id: String): LandingPadLocal?

    @Query("delete from landing_pads_table")
    suspend fun clearLandingPads()
}