package ru.alexmaryin.spacextimes_rx.data.model.lists

import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

@JsonClass(generateAdapter = true)
data class Crews(
    override val id: String,
    val name: String?,
    val image: String?,
) : HasStringId

