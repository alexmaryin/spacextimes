package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Relation

data class LaunchLocal(
    @Embedded val launch: LaunchWithoutRocket,
    @Relation(
        parentColumn = "rocketId",
        entityColumn = "rocketId"
    ) val rocket: RocketLocal?
) {
    fun toResponse() = launch.toResponse().also { it.rocket = rocket?.toResponse() }
}
