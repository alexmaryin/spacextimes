package ru.alexmaryin.spacextimes_rx.data.api.translator

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import ru.alexmaryin.spacextimes_rx.di.module.ApplicationModule

interface TranslatorApi {
    fun translate(source: String): String
}