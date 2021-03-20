package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.google.gson.annotations.SerializedName

enum class LandingPadType {
    @SerializedName("RTLS") LANDING_SITE,
    @SerializedName("ASDS") DRONE_SHIP,
}
