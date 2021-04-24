package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.LinksItem
import ru.alexmaryin.spacextimes_rx.databinding.LinksItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class LinksViewHolder : ViewHolderVisitor {

    override val layout: Int = R.layout.links_item

    override fun acceptBinding(item: Any): Boolean = item is LinksItem

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        with(binding as LinksItemBinding) {
            links = item as LinksItem
            this.clickListener = clickListener
        }
    }
}