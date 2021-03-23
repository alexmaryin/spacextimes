package ru.alexmaryin.spacextimes_rx.data.model.common

interface HasStringId {
    val id: String
    override fun equals(other: Any?): Boolean
}
