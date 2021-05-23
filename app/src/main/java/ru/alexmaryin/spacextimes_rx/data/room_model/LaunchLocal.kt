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
    ) val crew: List<CrewWithoutLaunches> = emptyList(),
    @Relation(
        parentColumn = "launchId",
        entityColumn = "capsuleId",
        associateBy = Junction(LaunchesToCapsules::class)
    ) val capsules: List<CapsuleWithoutLaunches> = emptyList(),
    @Relation(
        parentColumn = "launchId",
        entityColumn = "payloadId",
        associateBy = Junction(LaunchesToPayloads::class)
    ) val payloads: List<PayloadWithoutDragon> = emptyList(),
    @Relation(
        parentColumn = "launchId",
        entityColumn = "coreFlightId",
        associateBy = Junction(LaunchesToCoreFlights::class)
    ) val cores: List<CoreFlightWithoutDetails> = emptyList(),
) {
    fun toResponse() = launch.toResponse().also { launch ->
        launch.rocket = rocket?.toResponse()
        launch.launchPad = launchPad?.toResponse()
        launch.crew = crew.map { it.toResponse() }
        launch.capsules = capsules.map { it.toResponse() }
        launch.payloads = payloads.map { it.toResponse() }
        launch.cores = cores.map { it.toResponse() }
    }
}
