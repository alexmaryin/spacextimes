package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Payload
import ru.alexmaryin.spacextimes_rx.databinding.PayloadItemBindingImpl
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class PayloadViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.payload_item

    override fun acceptBinding(item: Any): Boolean = item is Payload

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        with(binding as PayloadItemBindingImpl) {
            payload = item as Payload
            this.clickListener = clickListener
        }
    }
}