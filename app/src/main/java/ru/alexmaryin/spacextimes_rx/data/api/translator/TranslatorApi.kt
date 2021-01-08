package ru.alexmaryin.spacextimes_rx.data.api.translator

interface TranslatorApi {
    suspend fun translate(source: String): String?
}