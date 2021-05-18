package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Failure(
    val time: Int?,
    val altitude: Int?,
    val reason: String?,
)