package ru.alexmaryin.spacextimes_rx.data.model.lists

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType

@JsonClass(generateAdapter = true)
data class LandingPads(
    override val id: String,
    val name: String?,
    val type: LandingPadType,
    val locality: String?,
    val region: String?,
    val latitude: Float?,
    val longitude: Float?,
    override val wikipedia: String?,
    @Transient override var wikiLocale: String? = null,
    override val details: String?,
    @Transient override var detailsRu: String? = null,
    @Json(name = "full_name") val fullName: String?,
) : HasStringId, HasDetails, HasWiki
