package ru.alexmaryin.spacextimes_rx.di

import javax.inject.Inject
import javax.inject.Singleton

const val SYNC_INTERVAL = 7200000   // 2 hour for sync interval

@Singleton
class Settings @Inject constructor() {
    var translateToRu: Boolean = false
    var armedSynchronize: Boolean = false
    var lastSync: Map<String, Long> = emptyMap()

    fun needSyncFor(cls: String) : Boolean {
        val q = System.currentTimeMillis() - (lastSync[cls] ?: 0) > SYNC_INTERVAL
        return armedSynchronize || q
    }
}