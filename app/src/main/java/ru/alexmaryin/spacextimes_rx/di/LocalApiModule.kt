package ru.alexmaryin.spacextimes_rx.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alexmaryin.spacextimes_rx.data.api.local.RoomConverters
import ru.alexmaryin.spacextimes_rx.data.api.local.spacex.SpaceXFlightsDao
import ru.alexmaryin.spacextimes_rx.data.api.local.spacex.SpaceXDatabase
import ru.alexmaryin.spacextimes_rx.data.api.local.translations.TranslateDao
import ru.alexmaryin.spacextimes_rx.data.api.local.translations.TranslateDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalApiModule {

    @Provides
    @Singleton
    fun provideTranslationsDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            TranslateDatabase::class.java,
            "translationsDb"
        )
            .addTypeConverter(RoomConverters())
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideTranslationsDao(db: TranslateDatabase): TranslateDao = db.dao

    @Provides
    @Singleton
    fun provideSpaceXDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            SpaceXDatabase::class.java,
            "spacexDb"
        )
            .addTypeConverter(RoomConverters())
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideSpaceXDao(db: SpaceXDatabase): SpaceXFlightsDao = db.dao
}