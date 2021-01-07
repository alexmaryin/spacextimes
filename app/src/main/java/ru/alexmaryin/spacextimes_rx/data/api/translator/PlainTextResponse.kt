package ru.alexmaryin.spacextimes_rx.data.api.translator

import com.google.gson.annotations.SerializedName

data class PlainTextResponse (
    val status: Int,
    val message: String,
    @SerializedName("mime_type") val mimeType: String,
    val data: String,
    @SerializedName("src_size") val srcSize: Int
)