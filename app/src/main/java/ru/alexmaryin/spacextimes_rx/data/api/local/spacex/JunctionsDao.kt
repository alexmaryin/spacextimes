package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.*
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.*

interface JunctionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCapsule(join: LaunchesToCapsules)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCore(join: LaunchesToCores)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCrew(join: LaunchesToCrew)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCoreFlight(join: LaunchesToCoreFlights)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToPayloads(join: LaunchesToPayloads)
}