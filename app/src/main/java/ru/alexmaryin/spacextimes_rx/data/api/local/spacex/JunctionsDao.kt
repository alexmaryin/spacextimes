package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.*
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.*

interface JunctionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCapsule(join: LaunchesToCapsules)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCapsule(joins: List<LaunchesToCapsules>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCore(join: LaunchesToCores)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCore(joins: List<LaunchesToCores>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchesToCrew(join: LaunchesToCrew)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCrew(joins: List<LaunchesToCrew>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToPayloads(join: LaunchesToPayloads)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToPayloads(joins: List<LaunchesToPayloads>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToLandingPads(joins: List<LaunchesToLandingPads>)
}