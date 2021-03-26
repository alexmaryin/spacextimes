package ru.alexmaryin.spacextimes_rx.data.api

data class ApiQuery(
    val query: Map<String, String> = emptyMap(),
    val options: Map<String, Any> = emptyMap(),
)
