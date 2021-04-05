package ru.alexmaryin.spacextimes_rx.ui.adapters

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

interface AdaptersManager {
    fun registerAdapter(itemType: Int, adapter: AdapterVisitor, layout: Int)
    fun getItemType(item: HasStringId): Int
    fun getAdapter(itemType: Int): AdapterItem
}