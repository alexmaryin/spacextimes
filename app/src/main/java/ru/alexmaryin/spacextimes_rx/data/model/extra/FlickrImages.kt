package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlickrImages(
    val small: List<String> = emptyList(),
    val original: List<String> = emptyList(),
)