package ru.alexmaryin.spacextimes_rx.data.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    val docs: List<T> = emptyList(),
    val totalDocs: Int = 0,
    val offset: Int = 0,
    val limit: Int = 10,
    val totalPages: Int = 1,
    val page: Int = 1,
    val pagingCounter: Int = 1,
    val hasPrevPage: Boolean = false,
    val hasNextPage: Boolean = false,
    val prevPage: Int?,
    val nextPage: Int?,
)
