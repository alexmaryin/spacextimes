package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.CapsuleLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.CapsuleWithLaunches
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchesToCapsules

interface CapsulesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsule(capsule: CapsuleLocal)

    @Transaction @Query("select * from capsules_table")
    suspend fun selectAllCapsules(): List<CapsuleWithLaunches>

    @Transaction @Query("select * from capsules_table where id=:id")
    suspend fun selectCapsule(id: String): CapsuleWithLaunches?

    @Query("delete from capsules_table")
    suspend fun clearCapsules()
}