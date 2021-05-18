package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PatchImages(
    val small: String?,
    val large: String?,
)