package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.alexmaryin.spacextimes_rx.data.api.local.RoomConverters
import ru.alexmaryin.spacextimes_rx.data.room_model.*
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.*

@Database(
    entities = [
        CapsuleWithoutLaunches::class,
        CoreWithoutLaunches::class,
        CrewWithoutLaunches::class,
        LandingPadWithoutLaunches::class,
        LaunchPadWithoutLaunches::class,
        LaunchWithoutDetails::class,
        LaunchesToCapsules::class,
        LaunchesToCores::class,
        LaunchesToCrew::class,
        LaunchesToPayloads::class,
        LaunchesToCoreFlights::class,
        LaunchesToLandingPads::class,
        HistoryLocal::class,
        RocketLocal::class,
        DragonLocal::class,
        PayloadDragonWithoutCapsule::class,
        PayloadWithoutDragon::class,
        CoreFlightWithoutDetails::class,
    ],
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    version = 2
)
@TypeConverters(RoomConverters::class)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract val dao: SpaceXDao
}