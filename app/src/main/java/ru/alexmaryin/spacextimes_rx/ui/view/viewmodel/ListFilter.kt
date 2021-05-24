package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

sealed class ListFilter
    class LaunchFilter(val filters: Set<LaunchFilterType>) : ListFilter()
    object EmptyFilter : ListFilter()
