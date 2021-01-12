package ru.alexmaryin.spacextimes_rx.ui.adapters


class AdapterClickListener<T>(val clickListener: (itemClicked: Int) -> Unit) {
    fun onClick(itemClicked: T) = clickListener(itemClicked.hashCode())
}