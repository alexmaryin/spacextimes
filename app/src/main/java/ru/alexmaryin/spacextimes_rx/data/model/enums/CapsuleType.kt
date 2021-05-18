package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class CapsuleType {
    @Json(name = "Dragon 1.0") DRAGON1_0,
    @Json(name = "Dragon 1.1") DRAGON1_1,
    @Json(name = "Dragon 2.0") DRAGON2_0
}