package ru.alexmaryin.spacextimes_rx.data.repository

import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.lists.*

interface ApiLocal {

        fun getCapsules(): List<Capsules>
        fun saveCapsules(capsules: List<Capsules>)
        fun getCapsuleById(id: String): Capsules?

        fun getCores(): List<Core>
        fun getCoreById(id: String): Core?

        fun getCrew(): List<Crews>
        fun getCrewById(id: String): Crew?

        fun getDragons(): List<Dragon>
        fun getDragonById(id: String): Dragon?

        fun getLaunchPads(): List<LaunchPads>
        fun getLaunchPadById(id: String): LaunchPad?

        fun getLandingPads(): List<LandingPads>
        fun getLandingPadById(id: String): LandingPad?

        fun getRockets(): List<Rocket>
        fun getRocketById(id: String): Rocket?

        fun getLaunches(): List<Launches>
        fun getLaunchById(id: String): Launch?

        fun getPayloads(): List<Payload>
        fun getPayloadById(id: String): Payload?

        fun getHistoryEvents(): List<History>
        fun getEventById(id: String): History?
}