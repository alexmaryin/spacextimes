package ru.alexmaryin.spacextimes_rx.data.api.local.translations

interface RoomTypeConvertor<T> {
     override fun toString(): String
     fun toData(): T
}