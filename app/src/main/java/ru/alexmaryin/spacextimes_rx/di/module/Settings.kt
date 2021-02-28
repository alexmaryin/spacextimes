package ru.alexmaryin.spacextimes_rx.di.module

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Settings @Inject constructor(){
    var translateToRu: Boolean = false
    var selectedItem: Any? = null
}