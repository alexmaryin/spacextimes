package ru.alexmaryin.spacextimes_rx.data.model.parts

import androidx.room.Embedded
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.LineSize

@JsonClass(generateAdapter = true)
data class CompositeFairing(
    @Embedded val height: LineSize,
    @Embedded val diameter: LineSize,
)