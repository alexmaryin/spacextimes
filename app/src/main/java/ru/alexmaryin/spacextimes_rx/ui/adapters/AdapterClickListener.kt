package ru.alexmaryin.spacextimes_rx.ui.adapters


class AdapterClickListenerById(val clickListener: (id: String) -> Unit) {
    fun onClick(id: String) = clickListener(id)
}