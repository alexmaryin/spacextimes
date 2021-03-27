package ru.alexmaryin.spacextimes_rx.data.model.extra

data class FlickrImages(
    val small: List<String> = emptyList(),
    val large: List<String> = emptyList(),
)