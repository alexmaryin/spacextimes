package ru.alexmaryin.spacextimes_rx.ui.view.filters

import ru.alexmaryin.spacextimes_rx.R

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
}