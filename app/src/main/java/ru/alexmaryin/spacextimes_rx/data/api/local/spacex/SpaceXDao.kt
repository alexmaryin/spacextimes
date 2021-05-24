package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Dao
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.room_model.LaunchesToCapsules

@Dao
abstract class SpaceXDao : CapsulesDao, CoresDao, CrewDao, LandingPadsDao, LaunchPadDao, LaunchDao,
    HistoryDao, RocketDao, DragonDao, PayloadDao, CoreFlightsDao, JunctionsDao {

    suspend fun insertCapsuleWithLaunches(capsule: Capsule) {
        insertCapsules(listOf(capsule.toRoom()))
        if (capsule.launches.isNotEmpty()) {
            insertLaunches(capsule.launches.map {
                insertLaunchesToCapsule(LaunchesToCapsules(it.id, capsule.id))
                it.toRoom().launch
            })
        }
    }
}