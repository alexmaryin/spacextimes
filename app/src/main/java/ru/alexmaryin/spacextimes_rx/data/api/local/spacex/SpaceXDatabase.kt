package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.alexmaryin.spacextimes_rx.data.api.local.RoomConverters
import ru.alexmaryin.spacextimes_rx.data.room_model.*

@Database(entities = [
        CapsuleWithoutLaunches::class,
        CoreLocal::class,
        CrewLocal::class,
        LandingPadLocal::class,
        LaunchPadLocal::class,
        LaunchWithoutRocket::class,
        LaunchesToCapsules::class,
        HistoryLocal::class,
        RocketLocal::class,
        DragonLocal::class,
        PayloadDragonWithoutCapsule::class,
        PayloadWithoutDragon::class,
    ],
    autoMigrations = [],
    version = 1)
@TypeConverters(RoomConverters::class)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract val dao: SpaceXDao
}