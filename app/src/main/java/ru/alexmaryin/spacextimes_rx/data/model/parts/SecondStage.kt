package ru.alexmaryin.spacextimes_rx.data.model.parts

import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Thrust

@JsonClass(generateAdapter = true)
data class SecondStage(
    @Embedded val thrust: Thrust,
    @Embedded val payloads: Payloads,
    val reusable: Boolean,
    val engines: Int,
    val fuelAmount: Float?,
    val burnTime: Int?,
)