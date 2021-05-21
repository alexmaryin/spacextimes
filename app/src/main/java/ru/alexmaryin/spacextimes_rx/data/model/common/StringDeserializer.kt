package ru.alexmaryin.spacextimes_rx.data.model.common

interface StringDeserializer <T> {
    operator fun invoke(source: String): T
}