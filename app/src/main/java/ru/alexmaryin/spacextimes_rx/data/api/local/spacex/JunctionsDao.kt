package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.*
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchesToCapsules

interface JunctionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCapsule(join: LaunchesToCapsules)

    @Transaction
    @Query("delete from launches_to_capsules_table")
    fun clearJunctions()
}