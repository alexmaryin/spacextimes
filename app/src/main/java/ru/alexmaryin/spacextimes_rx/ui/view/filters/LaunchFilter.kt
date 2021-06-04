package ru.alexmaryin.spacextimes_rx.ui.view.filters

import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launch

const val UPCOMING = R.string.upcoming_filter
const val SUCCESS = R.string.success_filter
const val PAST = R.string.past_filter
const val FAILED = R.string.failed_filter
const val NEXT_LAUNCH = R.string.next_launch_chip

object LaunchFilter : ListFilter() {

    override val filters = setOf(
        FilterChip(name = "Upcoming", resId = UPCOMING, isCheckable = true, checked = true),
        FilterChip(name = "Successfully", resId = SUCCESS, isCheckable = true, checked = true),
        FilterChip(name = "Past", resId = PAST, isCheckable = true, checked = true),
        FilterChip(name = "Failed", resId = FAILED, isCheckable = true, checked = true),
        FilterChip(name = "Next launch", resId = NEXT_LAUNCH, isCheckable = false, checked = false) {
            it.scrollToPosition { isFilterOn("Upcoming") }
        },
    )

    override fun <T> predicate(item: T) = with (item as Launch) {
        (upcoming == "Upcoming" in names || upcoming != "Past" in names) &&
                (success == "Successfully" in names || success != "Failed" in names) &&
                name.contains(searchString, ignoreCase = true)
    }
}