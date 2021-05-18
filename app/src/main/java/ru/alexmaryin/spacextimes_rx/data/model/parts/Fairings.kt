package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Fairings(
    val reused: Boolean?,
    val recovered: Boolean?,
    val ships: List<String> = emptyList(),
    @Json(name = "recovery_attempt") val recoveryAttempt: Boolean?,
)