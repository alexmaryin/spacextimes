package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
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
            getItems(_capsules, repository::getCapsules, processTranslate = { translateCapsulesLastUpdate(it) })
            return _capsules
        }

    /*
        At first, we must combine raw string with "translatable" fields of list of objects (i.e., lastUpdate for capsules),
        when pass it to translator api, and when split for a list again. At finish, we attach each translated string
        to each origin object.
     */
    private suspend fun translateCapsulesLastUpdate(capsules: List<Capsule>?): List<Capsule>? {
        capsules?.let { list ->
            if (settings.translateToRu) {
                withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                    val translatedList = list.filter { it.lastUpdate != null }.joinToString("\n") { capsule -> "${capsule.lastUpdate}"  }
                        .run { translator.translate(this) }?.split("\n") ?: emptyList()
                    list.filter { it.lastUpdate != null }.forEachIndexed { index, capsule ->  capsule.lastUpdateRu = translatedList[index] }
                }
            }
        }
        return capsules
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

    private fun <T> getItems(items: MutableLiveData<Result>,
                             invoker: KSuspendFunction0<Response<List<T>>>,
                             processTranslate: suspend (List<T>?) -> List<T>?) {
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