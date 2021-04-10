package ru.alexmaryin.spacextimes_rx.data.model.extra

data class FlickrImages(
    val small: List<String> = emptyList(),
    val original: List<String> = emptyList(),
)