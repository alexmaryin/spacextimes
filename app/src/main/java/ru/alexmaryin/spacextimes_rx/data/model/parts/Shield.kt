package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Shield(
    val material: String,
    @Json(name = "size_meters") val size: Float,
    @Json(name = "temp_degrees") val temperature: Int,
    @Json(name = "dev_partner") val developPartner: String
)