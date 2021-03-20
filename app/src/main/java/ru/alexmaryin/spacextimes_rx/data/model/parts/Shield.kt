package ru.alexmaryin.spacextimes_rx.data.model.parts

import com.google.gson.annotations.SerializedName

data class Shield(
    val material: String,
    @SerializedName("size_meters") val size: Float,
    @SerializedName("temp_degrees") val temperature: Int,
    @SerializedName("dev_partner") val developPartner: String
)