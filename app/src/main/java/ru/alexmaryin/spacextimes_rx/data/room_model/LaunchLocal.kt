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
        associateBy = Junction(CrewMembersToLaunch::class)
    ) val crew: List<CrewLocal>,
    @Relation(
        parentColumn = "launchId",
        entityColumn = "capsuleId",
        associateBy = Junction(LaunchesToCapsules::class)
    ) val capsules: List<CapsuleWithoutLaunches>
) {
    fun toResponse() = launch.toResponse().also {
        it.rocket = rocket?.toResponse()
        it.launchPad = launchPad?.toResponse()
        it.crew = crew.map { member -> member.toResponse() }
        it.capsules = capsules.map { capsule -> capsule.toResponse() }
    }
}
