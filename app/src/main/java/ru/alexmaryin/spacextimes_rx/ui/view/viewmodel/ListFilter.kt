package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import ru.alexmaryin.spacextimes_rx.R

const val UPCOMING = R.string.upcoming_filter
const val SUCCESS = R.string.success_filter
const val PAST = R.string.past_filter
const val FAILED = R.string.failed_filter
const val NEXT_LAUNCH = R.string.next_launch_chip

sealed class ListFilter {
    abstract val filters: Set<FilterChip>
    abstract fun isFilterOn(filter: String): Boolean
    abstract fun toggleLaunchFilter(filter: String)
}

object EmptyFilter : ListFilter() {
    override val filters = emptySet<FilterChip>()
    override fun isFilterOn(filter: String): Boolean = false
    override fun toggleLaunchFilter(filter: String) = Unit
}

object LaunchFilter : ListFilter() {

    override val filters = setOf(
        FilterChip(name = "Upcoming", resId = UPCOMING, isCheckable = true, checked = true),
        FilterChip(name = "Successfully", resId = SUCCESS, isCheckable = true, checked = true),
        FilterChip(name = "Past", resId = PAST, isCheckable = true, checked = true),
        FilterChip(name = "Failed", resId = FAILED, isCheckable = true, checked = true),
        FilterChip(name = "Next launch", resId = NEXT_LAUNCH, isCheckable = false, checked = false) { it.scrollNextLaunch() },
    )

    override fun isFilterOn(filter: String) =
        filters.firstOrNull { filter == it.name && it.isCheckable }?.checked ?: false

    override fun toggleLaunchFilter(filter: String) {
        filters.firstOrNull { it.name == filter }?.toggle()
    }

    val names get() =
        filters.fold(emptySet<String>().toMutableSet()) { set, chip -> if (chip.checked) set += chip.name; set }
}
