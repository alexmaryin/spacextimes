package ru.alexmaryin.spacextimes_rx.data.model.ui_items

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

data class CarouselItem(
    override val id: String = "carousel",
    val images: List<String>,
    val prefix: String,
    val writeGranted: Boolean
) : HasStringId
