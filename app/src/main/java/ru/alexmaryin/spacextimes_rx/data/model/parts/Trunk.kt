package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.Volume

data class Trunk(
    @SerializedName("trunk_volume") val volume: Volume,
    val cargo: Cargo
)