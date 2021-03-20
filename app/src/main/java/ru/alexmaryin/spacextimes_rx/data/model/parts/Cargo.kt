package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName

data class Cargo(
    @SerializedName("solar_array") val solarArray: Int,
    @SerializedName("unpressurized_cargo") val unpressurized: Boolean
)