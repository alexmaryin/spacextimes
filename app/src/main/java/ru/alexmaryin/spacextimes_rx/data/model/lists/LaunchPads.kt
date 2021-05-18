package ru.alexmaryin.spacextimes_rx.data.model.lists

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus

@JsonClass(generateAdapter = true)
data class LaunchPads(
    override val id: String,
    val name: String?,
    val locality: String?,
    val region: String,
    val latitude: Float,
    val longitude: Float,
    val rockets: List<String> = emptyList(),
    val launches: List<String> = emptyList(),
    override val details: String?,
    @Transient override var detailsRu: String? = null,
    val status: PadStatus,
    @Json(name = "timezone") val timeZone: String,
    @Json(name = "full_name") val fullName: String?,
    @Json(name = "launch_attempts") val launchAttempts: Int = 0,
    @Json(name = "launch_successes") val launchSuccesses: Int = 0,
) : HasStringId, HasDetails