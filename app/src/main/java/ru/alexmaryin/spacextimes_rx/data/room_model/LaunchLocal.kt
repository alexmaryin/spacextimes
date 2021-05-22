package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class LaunchLocal(
    @Embedded val launch: LaunchWithoutDetails,
    @Relation(
        parentColumn = "rocketId",
        entityColumn = "rocketId"
    ) val rocket: RocketLocal?,
    @Relation(
        parentColumn = "launchPadId",
        entityColumn = "launchPadId"
    ) val launchPad: LaunchPadLocal?,
    @Relation(
        parentColumn = "launchId",
        entityColumn = "crewId",
        associateBy = Junction(LaunchesToCrew::class)
    ) val crew: List<CrewWithoutLaunches>,
    @Relation(
        parentColumn = "launchId",
        entityColumn = "capsuleId",
        associateBy = Junction(LaunchesToCapsules::class)
    ) val capsules: List<CapsuleWithoutLaunches>,
    @Relation(
        parentColumn = "launchId",
        entityColumn = "coreId",
        associateBy = Junction(LaunchesToCores::class)
    ) val cores: List<CoreWithoutLaunches>,
) {
    fun toResponse() = launch.toResponse().also { launch ->
        launch.rocket = rocket?.toResponse()
        launch.launchPad = launchPad?.toResponse()
        launch.crew = crew.map { it.toResponse() }
        launch.capsules = capsules.map { it.toResponse() }
        //launch.cores = cores.map { it.toResponse() }
    }
}
