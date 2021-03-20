package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName

data class Fairings(
    val reused: Boolean = false,
    val recovered: Boolean = false,
    val ships: List<String> = emptyList(),
    @SerializedName("recovery_attempt") val recoveryAttempt: Boolean = false,
)