package ru.alexmaryin.spacextimes_rx.utils

fun Int.nullIfNegative(): Int? = if (this >= 0) this else null