package ru.alexmaryin.spacextimes_rx.ui.adapters

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

interface ViewHoldersManager {
    fun registerViewHolder(itemType: Int, viewHolder: ViewHolderVisitor)
    fun getItemType(item: HasStringId): Int
    fun getViewHolder(itemType: Int): ViewHolderVisitor
}