package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.module.Settings
import ru.alexmaryin.spacextimes_rx.utils.*
import kotlin.reflect.KSuspendFunction0

class SpaceXViewModel @ViewModelInject constructor(
    private val repository: SpacexDataRepository,
    private val networkHelper: NetworkHelper,
    private val settings: Settings,
    private val translator: TranslatorApi
) : ViewModel() {

    private val _capsules = MutableLiveData<Result>()
    val capsules: LiveData<Result>
        get() {
            getItems(_capsules, repository::getCapsules, processTranslate = {
               if (settings.translateToRu && (it != null)) {
                   it.forEach { capsule -> capsule.lastUpdateRu = capsule.lastUpdate?.let { lastUpdate -> translator.translate(lastUpdate) } }
               }
               it
            })
            return _capsules
        }

    private val _cores = MutableLiveData<Result>()
    val cores: LiveData<Result>
        get() {
            getItems(_cores, repository::getCores, { it })
            return _cores
        }

    private val _crew = MutableLiveData<Result>()
    val crew: LiveData<Result>
        get() {
            getItems(_crew, repository::getCrew, { it })
            return _crew
        }

    private val _dragons = MutableLiveData<Result>()
    val dragons: LiveData<Result>
        get() {
            getItems(_dragons, repository::getDragons, { it })
            return _dragons
        }

    private fun <T> getItems(items: MutableLiveData<Result>, invoker: KSuspendFunction0<Response<List<T>>>, processTranslate: (List<T>?) -> List<T>?) {
        viewModelScope.launch {
            items.postValue(Loading)
            if (networkHelper.isNetworkConnected()) {
                invoker().let {
                    if (it.isSuccessful) {
                        items.postValue(Success(processTranslate(it.body())))
                    } else items.postValue(Error(it.errorBody().toString()))

                }
            } else items.postValue(Error("No internet connection!"))
        }
    }
}