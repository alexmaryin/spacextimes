package ru.alexmaryin.spacextimes_rx.ui.adapters

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

data class RecyclerHeader(
    override val id: String = "header",
    val text: String
) : HasStringId
