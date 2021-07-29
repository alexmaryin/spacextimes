package ru.alexmaryin.spacextimes_rx.data.api.local.translations

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.alexmaryin.spacextimes_rx.data.api.local.RoomConverters

@Database(entities = [TranslateItem::class], version = 3, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class TranslateDatabase : RoomDatabase() {
    abstract val dao: TranslateDao
}