package ru.alexmaryin.spacextimes_rx.data.api.local.spacex

import androidx.room.*

@Dao
interface SpaceXDao : CapsulesDao, CoresDao, CrewDao, LandingPadsDao