package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reddit(
    val campaign: String?,
    val launch: String?,
    val media: String?,
    val recovery: String?,
)