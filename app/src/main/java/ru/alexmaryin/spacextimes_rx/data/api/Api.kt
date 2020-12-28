package ru.alexmaryin.spacextimes_rx.data.api

class Api(private val apiService: ApiService) {
    suspend fun getCapsules() = apiService.getCapsules()
}