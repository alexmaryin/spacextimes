package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.CarouselItem
import ru.alexmaryin.spacextimes_rx.databinding.CarouselRecyclerItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class CarouselViewHolder : ViewHolderVisitor {
    override val layout = R.layout.carousel_recycler_item

    override fun acceptBinding(item: Any) = item is CarouselItem

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        (binding as CarouselRecyclerItemBinding).item = item as CarouselItem
    }
}