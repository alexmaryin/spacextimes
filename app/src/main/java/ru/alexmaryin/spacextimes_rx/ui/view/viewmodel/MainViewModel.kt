package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.module.Settings
import ru.alexmaryin.spacextimes_rx.utils.*
import java.io.IOException
import kotlin.reflect.KSuspendFunction0

class SpaceXViewModel @ViewModelInject constructor(
    private val repository: SpacexDataRepository,
    private val networkHelper: NetworkHelper,
    private val settings: Settings,
    private val translator: TranslatorApi
) : ViewModel() {

    private var needRefresh: Boolean = false

    private val _capsules = MutableLiveData<Result>()
    val capsules: LiveData<Result>
        get() {
            if (_capsules.value == null || needRefresh) {
                getItems(_capsules, repository::getCapsules, { translateCapsulesLastUpdate(it) })
                needRefresh = false
            }
            return _capsules
        }

    /*
        At first, we must combine raw string with "translatable" fields of list of objects (i.e., lastUpdate for capsules),
        when pass it to translator api, and when split for a list again. At finish, we attach each translated string
        to each origin object.
     */
    private suspend fun translateCapsulesLastUpdate(capsules: List<Capsule>?): List<Capsule>? {
        capsules?.let { list ->
            if (settings.translateToRu)
                withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                    list.filter { it.lastUpdate != null }.joinToString("\n") { capsule -> capsule.lastUpdate.toString() }
                        .run { translator.translate(this) }?.split("\n")?.apply {
                            list.filter { it.lastUpdate != null }.zip(this) { capsule, ruStr -> capsule.lastUpdateRu = ruStr }
                        }
                }
        }
        return capsules
    }

    private val _cores = MutableLiveData<Result>()
    val cores: LiveData<Result>
        get() {
            if (_cores.value == null || needRefresh) {
                getItems(_cores, repository::getCores, { it })
                needRefresh = false
            }
            return _cores
        }

    private val _crew = MutableLiveData<Result>()
    val crew: LiveData<Result>
        get() {
            if (_crew.value == null || needRefresh) {
                getItems(_crew, repository::getCrew, { it })
                needRefresh = false
            }
            return _crew
        }

    private val _dragons = MutableLiveData<Result>()
    val dragons: LiveData<Result>
        get() {
            if (_dragons.value == null || needRefresh) {
                getItems(_dragons, repository::getDragons, { it })
                needRefresh = false
            }
            return _dragons
        }

    private fun <T> getItems(
        items: MutableLiveData<Result>,
        invoker: KSuspendFunction0<Response<List<T>>>,
        processTranslate: suspend (List<T>?) -> List<T>?
    ) {
        viewModelScope.launch {
            items.postValue(Loading)
            if (networkHelper.isNetworkConnected()) {
                invoker().let {
                    if (it.isSuccessful) {
                        try {
                            items.postValue(Success(processTranslate(it.body())))
                        } catch (e: IOException) {
                            items.postValue(Error("Translator error: ${e.localizedMessage}"))
                            yield()
                            items.postValue(Success(it.body()))
                        }

                    } else items.postValue(Error(it.errorBody().toString()))
                }
            } else items.postValue(Error("No internet connection!"))
        }
    }

    fun armRefresh() {
        needRefresh = true
    }
}