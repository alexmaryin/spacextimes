package ru.alexmaryin.spacextimes_rx.ui.adapters

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

sealed class DataItem {
    abstract val id: String
    data class  BodyItem <T : HasStringId>(val item: T) : DataItem() { override val id: String = item.id }
    data class Header(val header: RecyclerHeader) : DataItem() { override val id: String = "header" }
}

inline fun <reified T : HasStringId> DataItem.asData(): T? = if (this is DataItem.BodyItem<*>) item as T else null

fun <T:HasStringId> List<T>.asBody(): List<DataItem> = map { DataItem.BodyItem(it) }

fun String.asHeader(): DataItem = DataItem.Header(RecyclerHeader(text = this))