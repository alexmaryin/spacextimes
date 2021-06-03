package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.*
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.*

interface JunctionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCapsule(join: LaunchesToCapsules)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCapsule(joins: List<LaunchesToCapsules>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCore(join: LaunchesToCores)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCore(joins: List<LaunchesToCores>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCrew(join: LaunchesToCrew)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCrew(joins: List<LaunchesToCrew>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCoreFlight(join: LaunchesToCoreFlights)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToCoreFlight(joins: List<LaunchesToCoreFlights>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToPayloads(join: LaunchesToPayloads)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToPayloads(joins: List<LaunchesToPayloads>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchesToLandingPads(joins: List<LaunchesToLandingPads>)
}