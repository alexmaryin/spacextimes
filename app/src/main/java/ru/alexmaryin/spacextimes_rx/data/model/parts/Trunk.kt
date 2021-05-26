package ru.alexmaryin.spacextimes_rx.data.model.parts

import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Volume

@JsonClass(generateAdapter = true)
data class Trunk(
    @Embedded @Json(name = "trunk_volume") val volume: Volume,
    @Embedded val cargo: Cargo
)