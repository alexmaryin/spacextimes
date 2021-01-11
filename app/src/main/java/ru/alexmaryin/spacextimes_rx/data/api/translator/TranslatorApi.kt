package ru.alexmaryin.spacextimes_rx.data.api.translator

interface TranslatorApi {
    suspend fun fromString(source: String): String?
    suspend fun <T> fromList(source: List<T>, readItemToTranslate: (T) -> String,
                             updateItemWithTranslate: (T, String) -> Unit): List<T>?
}