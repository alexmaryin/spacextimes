package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class FairingVersion {
    @Json(name = "1.0") FAIRING_1_0,
    @Json(name = "2.0") FAIRING_2_0,
    @Json(name = "2.1") FAIRING_2_1,
}