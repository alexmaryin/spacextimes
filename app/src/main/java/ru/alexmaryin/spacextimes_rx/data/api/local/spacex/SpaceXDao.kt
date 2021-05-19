package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.*
import ru.alexmaryin.spacextimes_rx.data.room_model.CapsuleLocal

@Dao
interface SpaceXDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsule(capsule: CapsuleLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCapsule(capsule: CapsuleLocal)

    @Query("select * from capsules_table")
    suspend fun selectAllCapsules(): List<CapsuleLocal>

    @Query("select * from capsules_table where id=:id")
    suspend fun selectCapsule(id: String): CapsuleLocal?

    @Query("delete from capsules_table")
    suspend fun clearCapsules()
}