package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Failure(
    val time: Int?,
    val altitude: Int?,
    val reason: String?,
) {
    override fun toString() = "${time}:::${altitude}:::${reason}"

    companion object {
        fun fromString(source: String) = source.split(":::").run {
            Failure(
                time = get(0).toIntOrNull(),
                altitude = get(1).toIntOrNull(),
                reason = get(2)
            )
        }
    }
}