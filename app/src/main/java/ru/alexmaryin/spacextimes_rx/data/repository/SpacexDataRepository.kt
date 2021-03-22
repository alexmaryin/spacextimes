package ru.alexmaryin.spacextimes_rx.data.repository

import ru.alexmaryin.spacextimes_rx.data.api.LaunchesFilter
import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import javax.inject.Inject

class SpacexDataRepository @Inject constructor(private val api: SpaceXApi)  {

    suspend fun getCapsules() = api.getCapsules()
    suspend fun getCapsuleById(id: String) = api.getCapsuleById(id)

    suspend fun getCores() = api.getCores()
    suspend fun getCoreById(id: String) = api.getCoreById(id)

    suspend fun getCrew() = api.getCrew()
    suspend fun getCrewById(id: String) = api.getCrewById(id)

    suspend fun getDragons() = api.getDragons()
    suspend fun getDragonById(id: String) = api.getDragonById(id)

    suspend fun getRockets() = api.getRockets()
    suspend fun getRocketById(id: String) = api.getRocketById(id)

    suspend fun getLaunchPads() = api.getLaunchPads()
    suspend fun getLaunchPadById(id: String) = api.getLaunchPadById(id)

    suspend fun getLandingPads() = api.getLandingPads()
    suspend fun getLandingPadById(id: String) = api.getLandingPadById(id)

    suspend fun getLaunches() = api.getLaunches()
    suspend fun getLaunchById(id: String) = api.getLaunchById(id)

}