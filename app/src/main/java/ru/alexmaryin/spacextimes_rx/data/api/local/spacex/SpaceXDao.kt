package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.Dao

@Dao
interface SpaceXDao : CapsulesDao, CoresDao, CrewDao, LandingPadsDao, LaunchPadDao, LaunchDao,
        HistoryDao, RocketDao, DragonDao, PayloadDao, CoreFlightsDao