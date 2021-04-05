package ru.alexmaryin.spacextimes_rx.ui.adapters

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.RecyclerHeaderItemBinding

class HeaderViewHolder : AdapterVisitor {

    override fun bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById) {
        (binding as RecyclerHeaderItemBinding).headerItem = item as RecyclerHeader
    }

    override fun acceptVisitor(item: HasStringId): Boolean = item is RecyclerHeader
}