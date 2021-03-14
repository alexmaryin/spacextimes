package ru.alexmaryin.spacextimes_rx.data.api

import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.PlainTextResponse
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.Dragon

interface Api {

    suspend fun getCapsules(): Response<List<Capsule>>

    suspend fun getCores(): Response<List<Core>>

    suspend fun getCrew(): Response<List<Crew>>

    suspend fun getCrewById(id: String): Response<Crew>

    suspend fun getDragons(): Response<List<Dragon>>

    suspend fun getDragonById(id: String): Response<Dragon>

    suspend fun translate(source: String): Response<PlainTextResponse>
}