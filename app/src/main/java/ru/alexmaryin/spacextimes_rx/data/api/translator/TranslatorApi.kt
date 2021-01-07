package ru.alexmaryin.spacextimes_rx.data.api.translator

interface TranslatorApi {
    fun translate(source: String): String?
}