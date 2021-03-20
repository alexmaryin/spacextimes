package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName

data class Payloads(
    @SerializedName("option_1") val option: String,
    @SerializedName("composite_fairing") val compositeFairing: CompositeFairing,
)