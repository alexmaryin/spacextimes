package ru.alexmaryin.spacextimes_rx.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TranslateItem::class], version = 2, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class TranslateDatabase : RoomDatabase() {
    abstract val dao: TranslateDao
}