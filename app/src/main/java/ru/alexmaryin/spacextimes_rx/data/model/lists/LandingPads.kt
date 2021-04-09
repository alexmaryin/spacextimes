package ru.alexmaryin.spacextimes_rx.data.model.lists

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.enums.LandingPadType

data class LandingPads(
    override val id: String,
    val name: String,
    val type: LandingPadType,
    val locality: String,
    val region: String,
    val latitude: Float?,
    val longitude: Float?,
    override val wikipedia: String?,
    override var wikiLocale: String?,
    override val details: String?,
    override var detailsRu: String? = null,
    @SerializedName("full_name") val fullName: String,
) : HasStringId, HasDetails, HasWiki
