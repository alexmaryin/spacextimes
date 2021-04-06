package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.TwoStringsItem
import ru.alexmaryin.spacextimes_rx.databinding.TwoLineItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class TwoStringsViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.two_line_item

    override fun acceptBinding(item: HasStringId): Boolean = item is TwoStringsItem

    override fun bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById) {
        (binding as TwoLineItemBinding).model = item as TwoStringsItem
    }
}