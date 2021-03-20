package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.Volume

data class PressurizedCapsule(
    @SerializedName("payload_volume") val payload: Volume
)