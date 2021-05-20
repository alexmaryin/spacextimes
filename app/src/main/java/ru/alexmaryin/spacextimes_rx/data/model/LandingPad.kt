package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches
import ru.alexmaryin.spacextimes_rx.data.room_model.LandingPadLocal

@JsonClass(generateAdapter = true)
data class LandingPad(
    override val id: String,
    val name: String?,
    val type: LandingPadType,
    val locality: String?,
    val region: String?,
    val latitude: Float?,
    val longitude: Float?,
    val status: PadStatus?,
    val launches: List<Launches> = emptyList(),
    override val wikipedia: String?,
    @Transient override var wikiLocale: String? = null,
    override val details: String?,
    @Transient override var detailsRu: String? = null,
    @Json(name = "landing_attempts") val landingAttempts: Int = 0,
    @Json(name = "landing_successes") val landingSuccesses: Int = 0,
    @Json(name = "full_name") val fullName: String?,
) : HasStringId, HasDetails, HasWiki {

    fun toRoom() = LandingPadLocal(id, name, type, locality, region, latitude, longitude, status, wikipedia, details, detailsRu, landingAttempts, landingSuccesses, fullName)
}
