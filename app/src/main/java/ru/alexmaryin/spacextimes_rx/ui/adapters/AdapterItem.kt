package ru.alexmaryin.spacextimes_rx.ui.adapters

data class AdapterItem(
    val itemType: Int,
    val adapter: AdapterVisitor,
    val layout: Int,
)