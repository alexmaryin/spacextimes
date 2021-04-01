package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.google.gson.annotations.SerializedName

enum class FairingStatus {
    @SerializedName("active") ACTIVE,
    @SerializedName("inactive") INACTIVE,
    @SerializedName("unknown") UNKNOWN,
    @SerializedName("expended") EXPENDED,
    @SerializedName("lost") LOST,
    @SerializedName("retired") RETIRED
}