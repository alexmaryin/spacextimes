package ru.alexmaryin.spacextimes_rx.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectOnFragment(
    fragment: Fragment,
    state: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (T) -> Unit
) {
    fragment.lifecycleScope.launch {
        flowWithLifecycle(fragment.lifecycle, state)
            .collect {
                block(it)
            }
    }
}