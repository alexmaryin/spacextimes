package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Loading
import javax.inject.Inject

@HiltViewModel
class CapsuleDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
) : ViewModel() {

    fun getState() = repository.getCapsuleById(state.get("capsuleId") ?: "")
        .stateIn(viewModelScope, SharingStarted.Lazily, Loading)
}