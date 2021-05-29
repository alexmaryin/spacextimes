package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.di.SettingsRepository
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import javax.inject.Inject

@HiltViewModel
class CapsuleDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
    private val settings: SettingsRepository,
) : ViewModel() {

    private val details = MutableStateFlow<Result>(Loading)
    fun getDetails() = details.asStateFlow()

    fun load() = viewModelScope.launch {
        repository.getCapsuleById(state.get("capsuleId") ?: "")
            .collect { details.emit(it) }
    }

    fun composeList(res: Context, capsule: Capsule) = mutableListOf<HasStringId>().apply {
        with(capsule.launches) {
            if (isEmpty() || capsule.totalFlights != size) viewModelScope.launch {
                settings.armSynchronize = true
                load()
            } else {
                add(RecyclerHeader(text = res.getString(R.string.missions_list_header)))
                addAll(this)
            }
        }
    }
}