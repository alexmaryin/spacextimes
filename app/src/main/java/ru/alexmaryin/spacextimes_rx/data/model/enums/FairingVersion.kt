package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.google.gson.annotations.SerializedName

enum class FairingVersion {
    @SerializedName("1.0") FAIRING_1_0,
    @SerializedName("2.0") FAIRING_2_0,
    @SerializedName("2.1") FAIRING_2_1,
}