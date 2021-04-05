package ru.alexmaryin.spacextimes_rx.ui.adapters

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

class AdaptersManagerImpl : AdaptersManager {
    private val adapters = emptyList<AdapterItem>().toMutableList()

    override fun registerAdapter(itemType: Int, adapter: AdapterVisitor, layout: Int) {
        adapters.add(AdapterItem(itemType, adapter, layout))
    }

    override fun getItemType(item: HasStringId): Int {
        adapters.forEach {
            if(it.adapter.acceptVisitor(item)) return it.itemType
        }
        return ItemTypes.UNKNOWN
    }

    override fun getAdapter(itemType: Int) = adapters.find { it.itemType == itemType } ?: throw TypeCastException("Unknown recycler item type!")
}