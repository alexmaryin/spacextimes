package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity

@Entity(
    tableName = "crew_members_to_launch_table",
    primaryKeys = ["launchId", "crewId"]
)
data class CrewMembersToLaunch(
    val launchId: String,
    val crewId: String
)
