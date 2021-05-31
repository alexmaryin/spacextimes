package ru.alexmaryin.spacextimes_rx.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.alexmaryin.spacextimes_rx.data.api.local.ApiLocal
import ru.alexmaryin.spacextimes_rx.data.api.local.ApiLocalImpl
import ru.alexmaryin.spacextimes_rx.data.api.remote.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.api.remote.SpaceXApiImpl
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun provideApi(api: SpaceXApiImpl): SpaceXApi

    @Binds
    @ActivityRetainedScoped
    abstract fun provideLocalApi(api: ApiLocalImpl): ApiLocal

    @ExperimentalStdlibApi
    @Binds
    @ActivityRetainedScoped
    abstract fun provideTranslator(translator: TranslatorImpl): TranslatorApi
}

