package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.alexmaryin.spacextimes_rx.data.room_model.CapsuleLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.CapsuleWithoutLaunches

interface CapsulesDao {

    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsules(capsules: List<CapsuleWithoutLaunches>)

    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsule(capsule: CapsuleWithoutLaunches)

    @Transaction @Query("select * from capsules_table order by serial desc")
    suspend fun selectAllCapsules(): List<CapsuleLocal>

    @Transaction @Query("select * from capsules_table where capsuleId=:id")
    suspend fun selectCapsule(id: String): CapsuleLocal?

}