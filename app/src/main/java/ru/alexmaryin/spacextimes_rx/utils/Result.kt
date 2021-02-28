package ru.alexmaryin.spacextimes_rx.utils

sealed class Result
    data class Success<out T>(val data: T): Result()
    data class Error(val msg: String): Result()
    object Loading : Result()
