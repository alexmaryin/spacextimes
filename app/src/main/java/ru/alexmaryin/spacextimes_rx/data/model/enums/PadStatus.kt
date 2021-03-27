package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.google.gson.annotations.SerializedName

enum class PadStatus {
    @SerializedName("unknown") UNKNOWN,
    @SerializedName("active") ACTIVE,
    @SerializedName("inactive") INACTIVE,
    @SerializedName("retired") RETIRED,
    @SerializedName("lost") LOST,
    @SerializedName("under construction") UNDER_CONSTRUCTION,
}