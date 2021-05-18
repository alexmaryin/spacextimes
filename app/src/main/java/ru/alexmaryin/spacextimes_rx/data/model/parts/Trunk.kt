package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Volume

@JsonClass(generateAdapter = true)
data class Trunk(
    @Json(name = "trunk_volume") val volume: Volume,
    val cargo: Cargo
)