package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.google.gson.annotations.SerializedName

enum class OrbitType{
    @SerializedName("leo") LOW_EARTH,
    @SerializedName("gto") GEOSYNCHRONOUS,
    @SerializedName("mars") MARS,
    @SerializedName("pluto") PLUTO,

}