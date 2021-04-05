package ru.alexmaryin.spacextimes_rx.ui.adapters

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

interface AdapterVisitor {
    fun acceptVisitor(item: HasStringId): Boolean
    fun  bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById)
}