package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.google.gson.annotations.Expose

data class PatchImages(
    val small: String?,
    val large: String?,
//    @Expose(serialize = false, deserialize = false) var alternate: String?,
)