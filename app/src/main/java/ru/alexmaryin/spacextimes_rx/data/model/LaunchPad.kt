package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchPadWithoutLaunches

@JsonClass(generateAdapter = true)
data class LaunchPad(
    override val id: String,
    val name: String?,
    val locality: String?,
    val region: String,
    val latitude: Float,
    val longitude: Float,
    val status: PadStatus,
    override val details: String?,
    @Json(name = "timezone") val timeZone: String,
    @Json(name = "full_name") val fullName: String?,
    @Json(name = "launch_attempts") val launchAttempts: Int,
    @Json(name = "launch_successes") val launchSuccesses: Int,
    val rockets: List<Rocket> = emptyList(),
    var launches: List<Launch> = emptyList(),
    @Transient override var detailsRu: String? = null,
) : HasStringId, HasDetails {

    fun toRoom() = LaunchPadWithoutLaunches(id, name, locality, region, latitude, longitude, status, details, timeZone, fullName,
        launchAttempts, launchSuccesses)
}