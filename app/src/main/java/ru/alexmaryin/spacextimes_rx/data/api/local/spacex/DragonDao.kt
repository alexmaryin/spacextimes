package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.DragonLocal

interface DragonDao {
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDragons(dragons: List<DragonLocal>)

    @Transaction @Query("select * from dragons_table order by name")
    suspend fun selectAllDragons(): List<DragonLocal>

    @Transaction @Query("select * from dragons_table where dragonId=:id")
    suspend fun selectDragon(id: String): DragonLocal?
}