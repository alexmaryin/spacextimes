package ru.alexmaryin.spacextimes_rx.ui.adapters

import androidx.databinding.ViewDataBinding

interface ViewHolderVisitor {
    val layout: Int
    fun acceptBinding(item: Any): Boolean
    fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById)
}