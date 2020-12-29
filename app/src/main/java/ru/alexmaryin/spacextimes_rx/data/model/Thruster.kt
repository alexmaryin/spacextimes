package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.dimensions
import java.util.*

data class Thruster(
    val type: String;
    val amount: Int;
    val pods: Int;
    @serializeedName("fuel_1") val HotComponent: String;
    @serializeedName("fuel_2") val OxidizerComponent: String;
    val isp: Int;
    val thrust: Thrust;
)