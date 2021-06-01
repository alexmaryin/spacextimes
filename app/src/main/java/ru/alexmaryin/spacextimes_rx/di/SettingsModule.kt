package ru.alexmaryin.spacextimes_rx.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alexmaryin.spacextimes_rx.ProtoSettings
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context) = SettingsRepository(context.dataStore)

    private val Context.dataStore: DataStore<ProtoSettings> by dataStore(
        fileName = "settings.proto",
        serializer = SettingsSerializer
    )
}