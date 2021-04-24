package ru.alexmaryin.spacextimes_rx.utils

enum class ErrorType {
    REMOTE_API_ERROR,
    REMOTE_TRANSLATOR_ERROR,
    NO_INTERNET_CONNECTION,
    UPCOMING_LAUNCHES_DESELECTED,
    OTHER_ERROR
}
sealed class Result
    data class Success<out T>(val data: T): Result()
    data class Error(val msg: String, val error: ErrorType = ErrorType.OTHER_ERROR): Result()
    object Loading : Result()

inline fun <reified T> Result.toListOf(): List<T>? = if (this is Success<*>) (data as List<*>).map {  it as T } else null

fun <T> List<T>.toSuccess() = Success(this)

inline fun <reified T> Result.toDetails(): T = (this as Success<*>).data as T