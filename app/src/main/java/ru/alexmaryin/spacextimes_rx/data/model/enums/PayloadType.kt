package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.google.gson.annotations.SerializedName

enum class PayloadType {
    @SerializedName("Satellite") SATELLITE,
    @SerializedName("Dragon Boilerplate") DRAGON_BOILERPLATE,
    @SerializedName("Dragon 1.0") DRAGON_1_0,
    @SerializedName("Dragon 1.1") DRAGON_1_1,
    @SerializedName("Dragon 2.0") DRAGON_2_0,
    @SerializedName("Crew Dragon") CREW_DRAGON,
    @SerializedName("Lander") LANDER,
}