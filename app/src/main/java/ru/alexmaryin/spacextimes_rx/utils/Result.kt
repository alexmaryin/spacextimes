package ru.alexmaryin.spacextimes_rx.utils

sealed class Result
    data class Success<out T>(val data: T): Result()
    data class Error(val msg: String): Result()
    object Loading : Result()

inline fun <reified T> Result.toListOf(): List<T>? = if (this is Success<*>) (data as List<*>).map { it as T } else null

fun <T> List<T>.toSuccess() = Success(this)