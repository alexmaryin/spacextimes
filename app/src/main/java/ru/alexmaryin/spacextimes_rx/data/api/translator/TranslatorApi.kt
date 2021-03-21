package ru.alexmaryin.spacextimes_rx.data.api.translator

import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

interface TranslatorApi {
    suspend fun fromString(source: String): String?
    suspend fun <T> fromList(source: List<T>, readItemToTranslate: (T) -> String,
                             updateItemWithTranslate: (T, String) -> Unit): List<T>?
    suspend fun <T> translate(context: CoroutineContext, items: List<T>, from: KProperty1<T, String?>, to: KMutableProperty1<T, String?>)
}