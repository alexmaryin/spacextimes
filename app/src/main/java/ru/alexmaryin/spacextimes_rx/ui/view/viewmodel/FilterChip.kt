package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

data class FilterChip(
    val name: String,
    val resId: Int,
    val isCheckable: Boolean,
    var checked: Boolean,
    val onClick: (SpaceXViewModel) -> Unit = { it.armRefresh() },
) {
    fun toggle() { if (isCheckable) checked = !checked }
}
