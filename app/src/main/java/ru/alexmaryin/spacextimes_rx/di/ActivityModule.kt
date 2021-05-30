package ru.alexmaryin.spacextimes_rx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.alexmaryin.spacextimes_rx.data.api.local.ApiLocal
import ru.alexmaryin.spacextimes_rx.data.api.local.ApiLocalImpl
import ru.alexmaryin.spacextimes_rx.data.api.remote.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.api.remote.SpaceXApiImpl
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorImpl
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorInternalApi
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorInternalApiImpl
import ru.alexmaryin.spacextimes_rx.ui.adapters.ItemTypes
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManagerImpl
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders.*

@Module
@InstallIn(ActivityRetainedComponent::class)
class ActivityModule {

    @Provides
    @ActivityRetainedScoped
    fun provideApi(api: SpaceXApiImpl): SpaceXApi = api

    @Provides
    @ActivityRetainedScoped
    fun provideLocalApi(api: ApiLocalImpl): ApiLocal = api

    @Provides
    @ActivityRetainedScoped
    fun provideInternalTranslator(translator: TranslatorInternalApiImpl): TranslatorInternalApi = translator

    @Provides
    @ActivityRetainedScoped
    fun provideTranslator(translator: TranslatorImpl): TranslatorApi = translator

    @Provides
    @ActivityRetainedScoped
    fun provideAdaptersManager(): ViewHoldersManager = ViewHoldersManagerImpl().apply {
        registerViewHolder(ItemTypes.HEADER, HeaderViewHolder())
        registerViewHolder(ItemTypes.CAPSULE, CapsuleViewHolder())
        registerViewHolder(ItemTypes.CORE, CoreViewHolder())
        registerViewHolder(ItemTypes.CREW, CrewViewHolder())
        registerViewHolder(ItemTypes.DRAGON, DragonsViewHolder())
        registerViewHolder(ItemTypes.HISTORY_EVENT, HistoryEventsViewHolder())
        registerViewHolder(ItemTypes.LANDING_PAD, LandingPadViewHolder())
        registerViewHolder(ItemTypes.LAUNCH, LaunchesViewHolder())
        registerViewHolder(ItemTypes.LAUNCH_PAD, LaunchPadViewHolder())
        registerViewHolder(ItemTypes.ROCKET, RocketViewHolder())
        registerViewHolder(ItemTypes.TWO_STRINGS, TwoStringsViewHolder())
        registerViewHolder(ItemTypes.ONE_LINE_STRINGS, OneLine2ViewHolder())
        registerViewHolder(ItemTypes.LINKS, LinksViewHolder())
        registerViewHolder(ItemTypes.PAYLOAD, PayloadViewHolder())
        registerViewHolder(ItemTypes.CAROUSEL, CarouselViewHolder())
    }
}

