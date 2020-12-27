package ru.alexmaryin.spacextimes_rx.data.repository

import io.reactivex.Single
import ru.alexmaryin.spacextimes_rx.data.api.Api
import ru.alexmaryin.spacextimes_rx.data.model.Capsule

class SpacexDataRepository(private val api: Api) {
    fun getCapsules(): Single<List<Capsule>> = api.getCapsules()
}