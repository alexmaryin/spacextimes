package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.RecyclerHeaderItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class HeaderViewHolder : ViewHolderVisitor {

    override val layout = R.layout.recycler_header_item

    override fun bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById) {
        (binding as RecyclerHeaderItemBinding).headerItem = item as RecyclerHeader
    }

    override fun acceptBinding(item: HasStringId): Boolean = item is RecyclerHeader
}