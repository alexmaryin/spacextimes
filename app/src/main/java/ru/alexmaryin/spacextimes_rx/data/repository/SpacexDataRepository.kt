package ru.alexmaryin.spacextimes_rx.data.repository

import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import javax.inject.Inject

class SpacexDataRepository @Inject constructor(private val remoteApi: SpaceXApi)  {

    suspend fun getCapsules() = remoteApi.getCapsules()
    suspend fun getCapsuleById(id: String) = remoteApi.getCapsuleById(id)

    suspend fun getCores() = remoteApi.getCores()
    suspend fun getCoreById(id: String) = remoteApi.getCoreById(id)

    suspend fun getCrew() = remoteApi.getCrew()
    suspend fun getCrewById(id: String) = remoteApi.getCrewById(id)

    suspend fun getDragons() = remoteApi.getDragons()
    suspend fun getDragonById(id: String) = remoteApi.getDragonById(id)

    suspend fun getRockets() = remoteApi.getRockets()
    suspend fun getRocketById(id: String) = remoteApi.getRocketById(id)

    suspend fun getLaunchPads() = remoteApi.getLaunchPads()
    suspend fun getLaunchPadById(id: String) = remoteApi.getLaunchPadById(id)

    suspend fun getLandingPads() = remoteApi.getLandingPads()
    suspend fun getLandingPadById(id: String) = remoteApi.getLandingPadById(id)

    suspend fun getLaunches() = remoteApi.getLaunches()
    suspend fun getLaunchById(id: String) = remoteApi.getLaunchById(id)

}