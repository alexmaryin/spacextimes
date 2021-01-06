package ru.alexmaryin.spacextimes_rx.data.api.translator

import javax.inject.Inject

class TranslatorApiImpl  @Inject constructor (): TranslatorApi {
    override fun translate(source: String): String {
        return "переведено: $source"
    }
}