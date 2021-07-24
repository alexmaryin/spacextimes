package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.model.parts.CoreFlight
import ru.alexmaryin.spacextimes_rx.data.model.parts.CrewFlight
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToCapsules
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToPayloads

data class LaunchLocal(
    @Embedded val launch: LaunchWithoutDetails,
    @Relation(
        parentColumn = "rocketId",
        entityColumn = "rocketId"
    ) val rocket: RocketLocal?,
    @Relation(
        parentColumn = "launchPadId",
        entityColumn = "launchPadId"
    ) val launchPad: LaunchPadWithoutLaunches?,
    @Relation(
        parentColumn = "launchId",
        entityColumn = "launchId",
        associateBy = Junction(CrewFlightWithoutDetails::class)
    ) val crewFlight: Set<CrewFlightWithoutDetails> = emptySet(),
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
        entityColumn = "launchId",
        associateBy = Junction(CoreFlightWithoutDetails::class)
    ) val cores: Set<CoreFlightWithoutDetails> = emptySet(),
) {
    suspend fun toResponse(
        crewFlightSelect: (suspend (String, String) -> CrewFlight?),
        coreSelect: (suspend (String, String) -> CoreFlight?)
    ) = launch.toResponse().also { launch ->
        launch.rocket = rocket?.toResponse()
        launch.launchPad = launchPad?.toResponse()
        launch.capsules = capsules.map { it.toResponse().apply { launches += launch } }
        launch.payloads = payloads.map { it.toResponse() }
        launch.crewFlight = crewFlight.mapNotNull { crewFlightSelect(it.crewId, it.launchId) }
        launch.cores = cores.mapNotNull { coreSelect(it.coreId, it.launchId) }
    }
}
