package ru.alexmaryin.spacextimes_rx.data.model.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRequest(
    val query: ApiQuery? = null,
    val options: ApiOptions
)

@JsonClass(generateAdapter = true)
data class ApiQuery(
    @Json(name = "_id") val id: String,
)

@JsonClass(generateAdapter = true)
data class ApiOptions(
    var select: String? = null,
    var sort: String? = null,
    val offset: Int? = null,
    val page: Int? = null,
    val limit: Int? = null,
    val pagination: Boolean = false,
    val populate: List<PopulatedObject>? = null,
)

@JsonClass(generateAdapter = true)
data class PopulatedObject(
    val path: String,
    val select: String? = null,
    val populate: PopulatedObject? = null
)
