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
    ],
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ],
    version = 3)
@TypeConverters(RoomConverters::class)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract val dao: SpaceXDao
}