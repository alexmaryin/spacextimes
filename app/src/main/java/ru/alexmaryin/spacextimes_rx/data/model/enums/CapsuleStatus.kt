package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.google.gson.annotations.SerializedName

enum class CapsuleStatus {
    @SerializedName("unknown") UNKNOWN,
    @SerializedName("active") ACTIVE,
    @SerializedName("retired") RETIRED,
    @SerializedName("destroyed") DESTROYED
}