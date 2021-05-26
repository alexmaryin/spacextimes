package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

sealed class ListFilter {
    abstract val filters: MutableSet<String>
    abstract fun isFilterOn(filter: String): Boolean
    abstract fun toggleLaunchFilter(filter: String)
}

object EmptyFilter : ListFilter() {
    override val filters = emptySet<String>().toMutableSet()
    override fun isFilterOn(filter: String): Boolean = false
    override fun toggleLaunchFilter(filter: String) = Unit
}

object LaunchFilter : ListFilter() {

    override val filters = setOf("Upcoming", "Successfully", "Past", "Failed").toMutableSet()

    override fun isFilterOn(filter: String) = filter in filters

    override fun toggleLaunchFilter(filter: String) {
        if (filter in filters) filters -= filter else filters += filter
    }
}
