package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reddit(
    val campaign: String? = null,
    val launch: String? = null,
    val media: String? = null,
    val recovery: String? = null,
)