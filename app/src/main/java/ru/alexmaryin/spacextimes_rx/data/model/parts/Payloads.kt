package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Payloads(
    @Json(name = "option_1") val option: String,
    @Json(name = "composite_fairing") val compositeFairing: CompositeFairing,
)