package ru.alexmaryin.spacextimes_rx.data.model

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

data class Crews(
    override val id: String,
    val name: String,
    val image: String,
) : HasStringId

