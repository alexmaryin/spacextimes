package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.room_model.CrewFlightLocal
import ru.alexmaryin.spacextimes_rx.data.room_model.CrewFlightWithoutDetails

@JsonClass(generateAdapter = true)
data class CrewFlight(
    @Json(name = "crew") val member: Crew,
    val role: String
) {
    fun toRoom(launch: String) = CrewFlightLocal(
        CrewFlightWithoutDetails(member.id, launch, role), member.toRoom(), emptyList()
    )
}
