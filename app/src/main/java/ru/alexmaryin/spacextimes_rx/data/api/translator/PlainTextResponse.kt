package ru.alexmaryin.spacextimes_rx.data.api.translator

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlainTextResponse (
    val status: Int,
    val message: String,
    @Json(name = "mime_type") val mimeType: String,
    val data: String,
    @Json(name = "src_size") val srcSize: Int
)