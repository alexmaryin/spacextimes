package ru.alexmaryin.spacextimes_rx.ui.view.filters

import ru.alexmaryin.spacextimes_rx.R

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
        FilterChip(name = "Next launch", resId = NEXT_LAUNCH, isCheckable = false, checked = false) { it.scrollNextLaunch() },
    )
}