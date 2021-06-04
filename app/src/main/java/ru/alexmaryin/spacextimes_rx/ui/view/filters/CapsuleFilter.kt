package ru.alexmaryin.spacextimes_rx.ui.view.filters

import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus

const val CAPSULE_NOT_FLYING = R.string.no_flights_vehicle_filter
const val CAPSULE_FLYING = R.string.flying_vehicle_filter
const val CAPSULE_ACTIVE = R.string.active_vehicle_filter
const val CAPSULE_INACTIVE = R.string.inactive_vehicle_filter

object CapsuleFilter : ListFilter() {
    override val filters = setOf(
        FilterChip(name = "Not flying", resId = CAPSULE_NOT_FLYING, isCheckable = true, checked = true),
        FilterChip(name = "Flying", resId = CAPSULE_FLYING, isCheckable = true, checked = true),
        FilterChip(name = "Active", resId = CAPSULE_ACTIVE, isCheckable = true, checked = true),
        FilterChip(name = "Inactive", resId = CAPSULE_INACTIVE, isCheckable = true, checked = true),
    )

    override fun <T> predicate(item: T) = with (item as Capsule) {
        (totalFlights > 0 == "Flying" in names || totalFlights == 0 == "Not flying" in names) &&
                (status < CapsuleStatus.UNKNOWN == "Active" in names || status >= CapsuleStatus.UNKNOWN == "Inactive" in names)
    }
}