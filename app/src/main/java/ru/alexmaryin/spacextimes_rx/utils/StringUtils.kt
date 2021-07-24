package ru.alexmaryin.spacextimes_rx.utils

import ru.alexmaryin.spacextimes_rx.data.model.Core

const val MAX_FILE_SIZE = 14 * 1024

@ExperimentalStdlibApi
fun List<String>.toChunkedList(maxLength: Int = MAX_FILE_SIZE) = buildList {
    val temp = mutableListOf<String>()
    this@toChunkedList.forEach { line ->
        temp += line
        if (temp.toString().toByteArray().size >= maxLength) {
            add(temp.dropLast(1))
            temp.clear()
            temp += line
        }
    }
    add(temp)
}

fun String.onlyVerbs() = Regex("[^A-Za-z0-9 ]").replace(this, "")

fun falconResourceName(item: Core) = if (item.block == null) "falcon1" else buildString {
    append("falcon9block${item.block}")
    append(if (item.block > 4 || item.totalFlights >= 2) "legs_" else "_")
    append(
        when {
            item.totalFlights < 1 -> 1
            item.totalFlights > 10 -> 10
            else -> item.totalFlights
        }
    )
}