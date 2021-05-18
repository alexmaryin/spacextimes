package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.LineSize

@JsonClass(generateAdapter = true)
data class CompositeFairing(
    val height: LineSize,
    val diameter: LineSize,
)