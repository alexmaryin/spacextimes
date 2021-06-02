package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.extra.Thrust

@JsonClass(generateAdapter = true)
data class Thruster(
    val type: String,
    val amount: Int?,
    val pods: Int?,
    val isp: Int?,
    val thrust: Thrust,
    @Json(name = "fuel_1") val hotComponent: String,
    @Json(name = "fuel_2") val oxidizerComponent: String
) {
    override fun toString() = "${type}:::${amount}:::${pods}:::${isp}:::${thrust.kN}:::${thrust.lbf}:::${hotComponent}:::${oxidizerComponent}"

    companion object {
        fun fromString(source: String) = source.split(":::").run {
            Thruster(
                type = get(0),
                amount = get(1).toIntOrNull(),
                pods = get(2).toIntOrNull(),
                isp = get(3).toIntOrNull(),
                thrust = Thrust(kN = get(4).toFloatOrNull(), lbf = get(5).toIntOrNull()),
                hotComponent = get(6),
                oxidizerComponent = get(7)
            )
        }
    }
}