package ru.alexmaryin.spacextimes_rx.ui.adapters

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

class ViewHoldersManagerImpl : ViewHoldersManager {
    private val holdersMap = emptyMap<Int, ViewHolderVisitor>().toMutableMap()

    override fun registerViewHolder(itemType: Int, viewHolder: ViewHolderVisitor) {
        holdersMap += itemType to viewHolder
    }

    override fun getItemType(item: HasStringId): Int {
        holdersMap.forEach { (itemType, holder) ->
            if(holder.acceptBinding(item)) return itemType
        }
        return ItemTypes.UNKNOWN
    }

    override fun getViewHolder(itemType: Int) = holdersMap[itemType] ?: throw TypeCastException("Unknown recycler item type!")
}