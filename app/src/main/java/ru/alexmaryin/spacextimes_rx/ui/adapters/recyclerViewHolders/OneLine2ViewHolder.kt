package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.OneLineItem2
import ru.alexmaryin.spacextimes_rx.databinding.OneLineItem2Binding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class OneLine2ViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.one_line_item_2

    override fun acceptBinding(item: Any): Boolean = item is OneLineItem2

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        (binding as OneLineItem2Binding).model = item as OneLineItem2
    }
}