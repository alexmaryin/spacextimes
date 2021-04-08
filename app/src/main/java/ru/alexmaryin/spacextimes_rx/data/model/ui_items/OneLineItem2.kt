package ru.alexmaryin.spacextimes_rx.data.model.ui_items

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

data class OneLineItem2(
    override val id: String = "one line",
    val left: String,
    val right: String
) : HasStringId