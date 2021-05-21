package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmaryin.spacextimes_rx.data.room_model.DragonLocal

interface DragonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDragon(dragon: DragonLocal)

    @Query("select * from dragons_table")
    suspend fun selectAllDragons(): List<DragonLocal>

    @Query("select * from dragons_table where id=:id")
    suspend fun selectDragon(id: String): DragonLocal?

    @Query("delete from dragons_table")
    suspend fun clearDragons()
}