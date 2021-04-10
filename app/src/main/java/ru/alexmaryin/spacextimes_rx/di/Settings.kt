package ru.alexmaryin.spacextimes_rx.di

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Settings @Inject constructor() {
    var translateToRu: Boolean = false
    val currentListMap = emptyMap<String, List<HasStringId>>().toMutableMap()
}