package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.*
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

    private var needRefresh: Boolean = false
    var screen: Screen = Screen.Crew

    private val _capsules = MutableLiveData<Result>()
    val capsules: LiveData<Result>
        get() {
            if (_capsules.value == null || needRefresh) {
                getItems(_capsules, repository::getCapsules) { it?.apply { translateCapsulesLastUpdate(this) } }
                needRefresh = false
            }
            return _capsules
        }

    private suspend fun translateCapsulesLastUpdate(capsules: List<Capsule>) {
        if (settings.translateToRu) {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                val listForTranslating = capsules.filter { it.lastUpdate != null }
                translator.fromList(listForTranslating, readItemToTranslate = { "${it.lastUpdate}" },
                    updateItemWithTranslate = { capsule, translate -> capsule.lastUpdateRu = translate })
            }
        }
    }

    private val _cores = MutableLiveData<Result>()
    val cores: LiveData<Result>
        get() {
            if (_cores.value == null || needRefresh) {
                getItems(_cores, repository::getCores) { it?.apply { translateCoresLastUpdate(this) } }
                needRefresh = false
            }
            return _cores
        }

    private suspend fun translateCoresLastUpdate(cores: List<Core>) {
        if (settings.translateToRu) {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                val listForTranslating = cores.filter { it.lastUpdate != null }
                translator.fromList(listForTranslating, readItemToTranslate = { "${it.lastUpdate}" },
                    updateItemWithTranslate = { core, translate -> core.lastUpdateRu = translate })
            }
        }
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
                getItems(_dragons, repository::getDragons) { it?.apply { translateDragonsDescription(this) } }
                needRefresh = false
            }
            return _dragons
        }

    private suspend fun translateDragonsDescription(dragons: List<Dragon>) {
        if (settings.translateToRu) {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                val listForTranslating = dragons.filter { it.description != null }
                translator.fromList(listForTranslating, readItemToTranslate = { "${it.description}" },
                    updateItemWithTranslate = { dragon, translate -> dragon.descriptionRu = translate })
            }
        }
    }

    private val _rockets = MutableLiveData<Result>()
    val rockets: LiveData<Result>
        get() {
            if (_rockets.value == null || needRefresh) {
                getItems(_rockets, repository::getRockets) { it?.apply { translateRocketsDescription(this) } }
                needRefresh = false
            }
            return _rockets
        }

    private suspend fun translateRocketsDescription(rockets: List<Rocket>) {
        if (settings.translateToRu) {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                val listForTranslating = rockets.filter { it.description != null }
                translator.fromList(listForTranslating, readItemToTranslate = { "${it.description}" },
                    updateItemWithTranslate = { rocket, translate -> rocket.descriptionRu = translate })
            }
        }
    }

    private val _launchPads = MutableLiveData<Result>()
    val launchPads: LiveData<Result>
        get() {
            if (_launchPads.value == null || needRefresh) {
                getItems(_launchPads, repository::getLaunchPads) { it?.apply { translateLaunchPadDetails(this) } }
            }
            return _launchPads
        }

    private suspend fun translateLaunchPadDetails(launchPads: List<LaunchPad>) {
        if (settings.translateToRu) {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                val listForTranslating = launchPads.filter { it.details != null }
                translator.fromList(listForTranslating, readItemToTranslate = { "${it.details}" },
                    updateItemWithTranslate = { launchPad, translate -> launchPad.detailsRu = translate })
            }
        }
    }

    private val _landingPads = MutableLiveData<Result>()
    val landingPads: LiveData<Result>
        get() {
            if (_landingPads.value == null || needRefresh) {
                getItems(_landingPads, repository::getLandingPads) { it?.apply { translateLandingPadDetails(this) } }
            }
            return _landingPads
        }

    private suspend fun translateLandingPadDetails(landingPads: List<LandingPad>) {
        if (settings.translateToRu) {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                val listForTranslating = landingPads.filter { it.details != null }
                translator.fromList(listForTranslating, readItemToTranslate = { "${it.details}" },
                    updateItemWithTranslate = { landingPad, translate -> landingPad.detailsRu = translate })
            }
        }
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