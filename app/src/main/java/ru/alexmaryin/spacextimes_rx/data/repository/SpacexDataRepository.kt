package ru.alexmaryin.spacextimes_rx.data.repository

import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import javax.inject.Inject

class SpacexDataRepository @Inject constructor(private val api: SpaceXApi)  {

    suspend fun getCapsules() = api.getCapsules()

    suspend fun getCores() = api.getCores()

    suspend fun getCrew() = api.getCrew()

    suspend fun getCrewById(id: String) = api.getCrewById(id)

    suspend fun getDragons() = api.getDragons()

    suspend fun getDragonById(id: String) = api.getDragonById(id)
}