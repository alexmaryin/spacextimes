package ru.alexmaryin.spacextimes_rx.data.model.extra

data class Failure(
    val time: Float,
    val altitude: Float,
    val reason: String,
)