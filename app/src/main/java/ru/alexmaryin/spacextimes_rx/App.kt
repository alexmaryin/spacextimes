package ru.alexmaryin.spacextimes_rx

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    val settings = Settings()
}

data class Settings(
    var translateToRu: Boolean = false
)