package ru.alexmaryin.spacextimes_rx.ui.adapters

class AdapterClickListenerById(val clickListener: (id: String, listenerType: Int) -> Unit) {
    fun onClick(id: String, listenerType: Int) = clickListener(id, listenerType)
}

val  emptyClickListener = AdapterClickListenerById { _, _ -> }
