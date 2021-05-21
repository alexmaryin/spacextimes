package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "launches_to_capsules_table",
    primaryKeys = ["launchId", "capsuleId"],
    foreignKeys = [
        ForeignKey(
            entity = LaunchLocal::class,
            parentColumns = ["id"],
            childColumns = ["launchId"]
        ),
        ForeignKey(
            entity = CapsuleLocal::class,
            parentColumns = ["id"],
            childColumns = ["capsuleId"]
        )
    ]
)
data class LaunchesToCapsules(
    val launchId: String,
    val capsuleId: String,
)
