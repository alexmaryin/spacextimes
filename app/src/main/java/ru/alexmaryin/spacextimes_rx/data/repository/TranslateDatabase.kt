package ru.alexmaryin.spacextimes_rx.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TranslateItem::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class TranslateDatabase : RoomDatabase() {
    abstract val dao: TranslateDao

    companion object {

        @Volatile
        private var INSTANCE: TranslateDatabase? = null

        fun getInstance(context: Context): TranslateDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TranslateDatabase::class.java,
                    "translations_table"
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}