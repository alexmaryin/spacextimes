package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.alexmaryin.spacextimes_rx.data.api.local.RoomConverters
import ru.alexmaryin.spacextimes_rx.data.room_model.*

@Database(entities = [
        CapsuleLocal::class,
        CoreLocal::class,
        CrewLocal::class,
        LandingPadLocal::class,
        LaunchPadLocal::class,
        LaunchLocal::class,
        LaunchesToCapsules::class,
        HistoryLocal::class,
        RocketLocal::class,
        DragonLocal::class,
        PayloadDragonLocal::class,
        PayloadLocal::class,
    ],
    autoMigrations = [
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
    ],
    version = 5)
@TypeConverters(RoomConverters::class)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract val dao: SpaceXDao
}