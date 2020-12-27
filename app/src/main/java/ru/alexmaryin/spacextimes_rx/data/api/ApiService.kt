package ru.alexmaryin.spacextimes_rx.data.api

import io.reactivex.Single
import ru.alexmaryin.spacextimes_rx.data.model.capsule.Capsule

interface ApiService {

    fun getCapsules(): Single<List<Capsule>>
}