package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PatchImages(
    @Json(name = "small") val smallPatch: String? = null,
    @Json(name = "large") val largePatch: String? = null,
)