package ru.alexmaryin.spacextimes_rx.data.model.enums

import com.squareup.moshi.Json

enum class DatePrecision {
    @Json(name = "half") YEAR_HALF,
    @Json(name = "quarter") YEAR_QUARTER,
    @Json(name = "year") YEAR,
    @Json(name = "month") MONTH,
    @Json(name = "day") DAY,
    @Json(name = "hour") HOUR,
}