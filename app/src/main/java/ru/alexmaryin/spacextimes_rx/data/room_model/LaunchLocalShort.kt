package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Relation

data class LaunchLocalShort(
    @Embedded val launch: LaunchWithoutDetails,
    @Relation(
        parentColumn = "rocketId",
        entityColumn = "rocketId"
    ) val rocket: RocketLocal?,
) {
    fun toResponse() = launch.toResponse().also { launch ->
        launch.rocket = rocket?.toResponse()
    }
}
