package ru.alexmaryin.spacextimes_rx.ui.view.filters

import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.enums.CoreStatus

const val CORE_NOT_FLYING = R.string.no_flights_vehicle_filter
const val CORE_FLYING = R.string.flying_vehicle_filter
const val CORE_ACTIVE = R.string.active_vehicle_filter
const val CORE_INACTIVE = R.string.inactive_vehicle_filter

object CoreFilter : ListFilter() {
    override val filters = setOf(
        FilterChip(name = "Not flying", resId = CORE_NOT_FLYING, isCheckable = true, checked = true),
        FilterChip(name = "Flying", resId = CORE_FLYING, isCheckable = true, checked = true),
        FilterChip(name = "Active", resId = CORE_ACTIVE, isCheckable = true, checked = true),
        FilterChip(name = "Inactive", resId = CORE_INACTIVE, isCheckable = true, checked = true),
    )

    override fun <T> predicate(item: T) = with (item as Core) {
        (totalFlights > 0 == "Flying" in names || totalFlights == 0 == "Not flying" in names) &&
                (status < CoreStatus.INACTIVE == "Active" in names || status >= CoreStatus.INACTIVE == "Inactive" in names) &&
                serial.contains(searchString, ignoreCase = true)
    }
}