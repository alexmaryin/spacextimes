package ru.alexmaryin.spacextimes_rx.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Success

class CapsulesViewModel(private val repository: SpacexDataRepository): ViewModel() {

    fun getCapsules() = liveData(Dispatchers.IO) {
        emit(Loading)
        try {
            emit(Success(data = repository.getCapsules()))
        } catch (e: Exception) {
            emit(Error(msg = "Error during fetch data from internet:\n${e.localizedMessage}"))
        }
    }
}