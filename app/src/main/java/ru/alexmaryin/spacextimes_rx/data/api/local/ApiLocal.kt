package ru.alexmaryin.spacextimes_rx.data.api.local

import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.lists.*

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
    suspend fun getDragonById(id: String): Dragon?

    suspend fun getLaunchPads(): List<LaunchPads>
    suspend fun getLaunchPadById(id: String): LaunchPad?

    suspend fun getLandingPads(): List<LandingPads>
    suspend fun getLandingPadById(id: String): LandingPad?

    suspend fun getRockets(): List<Rocket>
    suspend fun getRocketById(id: String): Rocket?

    suspend fun getLaunches(): List<Launches>
    suspend fun getLaunchById(id: String): Launch?

    suspend fun getPayloadById(id: String): Payload?

    suspend fun getHistoryEvents(): List<History>
}