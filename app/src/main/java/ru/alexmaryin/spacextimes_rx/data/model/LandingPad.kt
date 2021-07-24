package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType
import ru.alexmaryin.spacextimes_rx.data.model.enums.PadStatus
import ru.alexmaryin.spacextimes_rx.data.model.extra.Images
import ru.alexmaryin.spacextimes_rx.data.room_model.LandingPadWithoutLaunches

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
    var launches: List<Launch> = emptyList(),
    override val wikipedia: String?,
    override val details: String?,
    @Json(name = "landing_attempts") val landingAttempts: Int,
    @Json(name = "landing_successes") val landingSuccesses: Int,
    @Json(name = "full_name") val fullName: String?,
    val images: Images,
    @Transient override var wikiLocale: String? = null,
    @Transient override var detailsRu: String? = null,
) : HasStringId, HasDetails, HasWiki {

    fun toRoom() = LandingPadWithoutLaunches(id, name, type, locality, region, latitude, longitude, status, wikipedia, details,
        landingAttempts, landingSuccesses, fullName, images)
}
