package ru.alexmaryin.spacextimes_rx.data.model.ui_items

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

data class TwoStringsItem(
    override val id: String = "two strings",
    val caption: String,
    val details: String
) : HasStringId
