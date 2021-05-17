package ru.alexmaryin.spacextimes_rx.data.api.translator

import kotlinx.coroutines.flow.Flow
import ru.alexmaryin.spacextimes_rx.utils.Result

interface TranslatorApi {
    fun Flow<Result>.translateDetails(): Flow<Result>
    fun Flow<Result>.translateLastUpdate(): Flow<Result>
    fun Flow<Result>.translateDescription(): Flow<Result>
    fun Flow<Result>.translateTitle(): Flow<Result>
}