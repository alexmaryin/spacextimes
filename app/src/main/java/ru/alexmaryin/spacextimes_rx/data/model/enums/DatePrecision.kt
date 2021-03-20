package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.google.gson.annotations.SerializedName

enum class DatePrecision {
    @SerializedName("half") YEAR_HALF,
    @SerializedName("quarter") YEAR_QUARTER,
    @SerializedName("year") YEAR,
    @SerializedName("month") MONTH,
    @SerializedName("day") DAY,
    @SerializedName("hour") HOUR,
}