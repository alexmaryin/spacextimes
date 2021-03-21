package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.*
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction0

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    private val repository: SpacexDataRepository,
    private val networkHelper: NetworkHelper,
    private val settings: Settings,
    private val translator: TranslatorApi
) : ViewModel() {

    private suspend fun translateLastUpdate(items: List<HasLastUpdate>) {
        if (settings.translateToRu)
            translator.translate(viewModelScope.coroutineContext, items, HasLastUpdate::lastUpdate, HasLastUpdate::lastUpdateRu)
    }

    private suspend fun translateDescription(items: List<HasDescription>) {
        if (settings.translateToRu)
            translator.translate(viewModelScope.coroutineContext, items, HasDescription::description, HasDescription::descriptionRu)
    }

    private suspend fun translateDetails(items: List<HasDetails>) {
        if (settings.translateToRu)
            translator.translate(viewModelScope.coroutineContext, items, HasDetails::details, HasDetails::detailsRu)
    }

    private var needRefresh: Boolean = false
    var screen: Screen = Screen.Crew

    private val _capsules = MutableLiveData<Result>()
    val capsules: LiveData<Result>
        get() {
            if (_capsules.value == null || needRefresh) {
                getItems(_capsules, repository::getCapsules) { it?.apply { translateLastUpdate(this) } }
                needRefresh = false
            }
            return _capsules
        }

    private val _cores = MutableLiveData<Result>()
    val cores: LiveData<Result>
        get() {
            if (_cores.value == null || needRefresh) {
                getItems(_cores, repository::getCores) { it?.apply { translateLastUpdate(this) } }
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
                getItems(_dragons, repository::getDragons) { it?.apply { translateDescription(this) } }
                needRefresh = false
            }
            return _dragons
        }

    private val _rockets = MutableLiveData<Result>()
    val rockets: LiveData<Result>
        get() {
            if (_rockets.value == null || needRefresh) {
                getItems(_rockets, repository::getRockets) { it?.apply { translateDescription(this) } }
                needRefresh = false
            }
            return _rockets
        }

    private val _launchPads = MutableLiveData<Result>()
    val launchPads: LiveData<Result>
        get() {
            if (_launchPads.value == null || needRefresh) {
                getItems(_launchPads, repository::getLaunchPads) { it?.apply { translateDetails(this) } }
            }
            return _launchPads
        }

    private val _landingPads = MutableLiveData<Result>()
    val landingPads: LiveData<Result>
        get() {
            if (_landingPads.value == null || needRefresh) {
                getItems(_landingPads, repository::getLandingPads) { it?.apply { translateDetails(this) } }
            }
            return _landingPads
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