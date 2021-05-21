package ru.alexmaryin.spacextimes_rx.data.model.parts

import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Volume

@JsonClass(generateAdapter = true)
data class PressurizedCapsule(
    @Embedded @Json(name = "payload_volume") val payload: Volume
)