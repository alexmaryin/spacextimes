package ru.alexmaryin.spacextimes_rx.di

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Settings @Inject constructor() {
    var translateToRu: Boolean = false
}