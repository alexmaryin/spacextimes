package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    val small: List<String> = emptyList(),
    val large: List<String> = emptyList(),
)
