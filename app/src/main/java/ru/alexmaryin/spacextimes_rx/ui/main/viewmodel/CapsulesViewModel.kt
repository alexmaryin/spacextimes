package ru.alexmaryin.spacextimes_rx.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.*

class CapsulesViewModel @ViewModelInject constructor(
    private val repository: SpacexDataRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _capsules = MutableLiveData<Result>()
    val capsules: LiveData<Result>
        get() {
            getCapsules()
            return _capsules
        }

    fun getCapsules() {
        viewModelScope.launch {
            _capsules.postValue(Loading)
            if (networkHelper.isNetworkConnected()) {
                repository.getCapsules().let {
                    if (it.isSuccessful) {
                        _capsules.postValue(Success(it.body()))
                    } else _capsules.postValue(Error(it.errorBody().toString()))

                }
            } else _capsules.postValue(Error("No internet connection!"))
        }
    }

    fun getCores() {}

    fun getCrew() {}

    fun getDragons() {}
}