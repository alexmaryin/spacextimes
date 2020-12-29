package ru.alexmaryin.spacextimes_rx.data.api

class Api(private val apiService: ApiService) {

    suspend fun getCapsules() = apiService.getCapsules()

    suspend fun getCores() = apiService.getCores()

    suspend fun getCrew() = apiService.getCrew()

    suspend fun getDragons() = apiService.getDragons()
}