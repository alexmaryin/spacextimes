package ru.alexmaryin.spacextimes_rx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.alexmaryin.spacextimes_rx.ui.adapters.ItemTypes
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManagerImpl
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders.*

@Module
@InstallIn(ActivityRetainedComponent::class)
class AdaptersModule {
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