package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchCrewLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchCrewWithoutDetails

@JsonClass(generateAdapter = true)
data class LaunchCrew(
    @Json(name = "crew") val member: Crew,
    val role: String
) {
    fun toRoom() = LaunchCrewLocal(
        LaunchCrewWithoutDetails(member.id, role), member.toRoom(), emptyList()
    )
}
