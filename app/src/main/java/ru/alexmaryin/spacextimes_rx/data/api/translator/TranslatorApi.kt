package ru.alexmaryin.spacextimes_rx.data.api.translator

import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasTitle

interface TranslatorApi {
    suspend fun translateDetails(items: List<HasDetails>?)
    suspend fun translateLastUpdate(items: List<HasLastUpdate>?)
    suspend fun translateDescription(items: List<HasDescription>?)
    suspend fun translateTitle(items: List<HasTitle>?)
}