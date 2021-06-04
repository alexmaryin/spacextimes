package ru.alexmaryin.spacextimes_rx.ui.view.filters

sealed class ListFilter {
    abstract val filters: Set<FilterChip>
    abstract fun <T> predicate(item: T): Boolean
    open var searchString = ""

    val names get() = filters.fold(emptySet<String>().toMutableSet()) { set, chip -> if (chip.checked) set += chip.name; set }

    open fun isFilterOn(filter: String) = filters.firstOrNull { filter == it.name && it.isCheckable }?.checked ?: false
}

object EmptyFilter : ListFilter() {
    override val filters = emptySet<FilterChip>()
    override fun <T> predicate(item: T): Boolean = false
}