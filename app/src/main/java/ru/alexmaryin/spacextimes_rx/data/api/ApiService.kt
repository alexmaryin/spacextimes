package ru.alexmaryin.spacextimes_rx.data.api

import io.reactivex.Single
import ru.alexmaryin.spacextimes_rx.data.model.Capsule

interface ApiService {

    fun getCapsules(): Single<List<Capsule>>
}