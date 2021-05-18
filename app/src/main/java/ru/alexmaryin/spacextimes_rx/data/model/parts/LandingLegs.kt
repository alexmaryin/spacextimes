package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LandingLegs(
    val number: Int,
    val material: String?,
)