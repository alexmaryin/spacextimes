package ru.alexmaryin.spacextimes_rx.utils

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