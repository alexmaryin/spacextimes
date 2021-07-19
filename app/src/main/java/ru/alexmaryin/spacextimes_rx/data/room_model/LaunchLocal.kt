package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.model.parts.CoreFlight
import ru.alexmaryin.spacextimes_rx.data.model.parts.LaunchCrew
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToCapsules
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToCoreFlights
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToCrew
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
        entityColumn = "crewId",
        associateBy = Junction(LaunchesToCrew::class)
    ) val crew: List<LaunchCrewWithoutDetails> = emptyList(),
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
    suspend fun toResponse(
        crewSelect: (suspend (String) -> LaunchCrew)? = null,
        coreSelect: (suspend (Int) -> CoreFlight?)? = null
    ) = launch.toResponse().also { launch ->
        launch.rocket = rocket?.toResponse()
        launch.launchPad = launchPad?.toResponse()
        launch.capsules = capsules.map { it.toResponse().apply { launches += launch } }
        launch.payloads = payloads.map { it.toResponse() }
        crewSelect?.let {
            launch.crew = crew.map { crewSelect(it.crewId) }
        }
        coreSelect?.let {
            launch.cores = cores.mapNotNull { coreSelect(it.coreFlightId) }
        }
    }
}
