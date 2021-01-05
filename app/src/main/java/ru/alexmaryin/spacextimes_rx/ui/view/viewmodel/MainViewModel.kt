package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.*
import kotlin.reflect.KSuspendFunction0

class SpaceXViewModel @ViewModelInject constructor(
    private val repository: SpacexDataRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _capsules = MutableLiveData<Result>()
    val capsules: LiveData<Result>
        get() {
            getItems(_capsules, repository::getCapsules)
            return _capsules
        }

    private val _cores = MutableLiveData<Result>()
    val cores: LiveData<Result>
        get() {
            getItems(_cores, repository::getCores)
            return _cores
        }

    private val _crew = MutableLiveData<Result>()
    val crew: LiveData<Result>
        get() {
            getItems(_crew, repository::getCrew)
            return _crew
        }

    private val _dragons = MutableLiveData<Result>()
    val dragons: LiveData<Result>
        get() {
            getItems(_dragons, repository::getDragons)
            return _dragons
        }

    private fun <T> getItems(items: MutableLiveData<Result>, invoker: KSuspendFunction0<Response<List<T>>>) {
        viewModelScope.launch {
            items.postValue(Loading)
            if (networkHelper.isNetworkConnected()) {
                invoker().let {
                    if (it.isSuccessful) {
                        items.postValue(Success(it.body()))
                    } else items.postValue(Error(it.errorBody().toString()))

                }
            } else items.postValue(Error("No internet connection!"))
        }
    }
}