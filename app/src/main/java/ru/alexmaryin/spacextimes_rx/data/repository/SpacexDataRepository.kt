package ru.alexmaryin.spacextimes_rx.data.repository

import ru.alexmaryin.spacextimes_rx.data.api.Api

class SpacexDataRepository(private val api: Api) {

    suspend fun getCapsules() = api.getCapsules()

    suspend fun getCores() = api.getCores()

    suspend fun getCrew() = api.getCrew()

    suspend fun getDragons() = api.getDragons()
}