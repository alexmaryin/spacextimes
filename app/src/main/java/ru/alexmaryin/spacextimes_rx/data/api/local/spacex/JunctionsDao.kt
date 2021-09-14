package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.*
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.*

interface JunctionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCapsule(join: LaunchesToCapsules)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCapsule(joins: List<LaunchesToCapsules>)

    @Query("delete from launches_to_capsules_table")
    suspend fun clearLaunchesToCapsules()

    @Query("delete from launches_to_capsules_table where launchId=:id")
    suspend fun clearLaunchToCapsules(id: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCore(join: LaunchesToCores)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCore(joins: List<LaunchesToCores>)

    @Query("delete from launches_to_cores_table")
    suspend fun clearLaunchesToCores()

    @Query("delete from launches_to_cores_table where launchId=:id")
    suspend fun clearLaunchToCores(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchesToCrew(join: LaunchesToCrew)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToCrew(joins: List<LaunchesToCrew>)

    @Query("delete from launches_to_crew_table")
    suspend fun clearLaunchesToCrew()

    @Query("delete from launches_to_crew_table where launchId=:id")
    suspend fun clearLaunchToCrew(id: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToPayloads(join: LaunchesToPayloads)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToPayloads(joins: List<LaunchesToPayloads>)

    @Query("delete from launches_to_payloads_table")
    suspend fun clearLaunchesToPayloads()

    @Query("delete from launches_to_payloads_table where launchId=:id")
    suspend fun clearLaunchToPayloads(id: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunchesToLandingPads(joins: List<LaunchesToLandingPads>)

    @Query("delete from launches_to_landing_pads_table")
    suspend fun clearLaunchesToLandingPads()
}