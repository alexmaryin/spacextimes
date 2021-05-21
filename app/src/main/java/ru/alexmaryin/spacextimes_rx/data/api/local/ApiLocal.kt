package ru.alexmaryin.spacextimes_rx.data.api.local

import ru.alexmaryin.spacextimes_rx.data.model.*

interface ApiLocal {

    suspend fun getCapsules(): List<Capsule>
    suspend fun saveCapsules(capsules: List<Capsule>)
    suspend fun getCapsuleById(id: String): Capsule?

    suspend fun getCores(): List<Core>
    suspend fun saveCores(cores: List<Core>)
    suspend fun getCoreById(id: String): Core?

    suspend fun getCrew(): List<Crew>
    suspend fun saveCrew(crew: List<Crew>)
    suspend fun getCrewById(id: String): Crew?

    suspend fun getDragons(): List<Dragon>
    suspend fun saveDragons(dragons: List<Dragon>)
    suspend fun getDragonById(id: String): Dragon?

    suspend fun getLaunchPads(): List<LaunchPad>
    suspend fun saveLaunchPads(pads: List<LaunchPad>)
    suspend fun getLaunchPadById(id: String): LaunchPad?

    suspend fun getLandingPads(): List<LandingPad>
    suspend fun saveLandingPads(pads: List<LandingPad>)
    suspend fun getLandingPadById(id: String): LandingPad?

    suspend fun getRockets(): List<Rocket>
    suspend fun saveRockets(rockets: List<Rocket>)
    suspend fun getRocketById(id: String): Rocket?

    suspend fun getLaunches(): List<Launch>
    suspend fun saveLaunches(launches: List<Launch>)
    suspend fun getLaunchById(id: String): Launch?

    suspend fun getPayloadById(id: String): Payload?

    suspend fun getHistoryEvents(): List<History>
    suspend fun saveHistoryEvents(events: List<History>)
}