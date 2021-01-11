package ru.alexmaryin.spacextimes_rx.data.api.translator

interface TranslatorApi {
    suspend fun fromString(source: String): String?
    suspend fun <T> fromList(source: List<T>, readItem: (T) -> String, writeItem: (T, String) -> Unit): List<T>?
}