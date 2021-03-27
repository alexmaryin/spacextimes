package ru.alexmaryin.spacextimes_rx.data.api

data class ApiResponse<T>(
    val docs: List<T>,
    val totalDocs: Int,
    val offset: Int,
    val limit: Int,
    val totalPages: Int,
    val page: Int,
    val pagingCounter: Int,
    val hasPrevPage: Boolean,
    val hasNextPage: Boolean,
    val prevPage: Int?,
    val nextPage: Int?,
)
