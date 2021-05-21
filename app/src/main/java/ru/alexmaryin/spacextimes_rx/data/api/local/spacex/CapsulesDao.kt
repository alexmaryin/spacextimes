package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.CapsuleLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchesToCapsules

interface CapsulesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsule(capsule: CapsuleLocal)

    @Query("select * from capsules_table join launches_to_capsules_table on capsuleId=id")
    suspend fun selectAllCapsules(): List<CapsuleLocal>

    @Query("select * from capsules_table where id=:id")
    suspend fun selectCapsule(id: String): CapsuleLocal?

    @Query("delete from capsules_table")
    suspend fun clearCapsules()
}