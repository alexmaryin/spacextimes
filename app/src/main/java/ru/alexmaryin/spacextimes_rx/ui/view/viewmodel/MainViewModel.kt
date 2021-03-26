package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.Launch
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

    private fun <T> collectData(
        field: MutableLiveData<Result>,
        invoker: KSuspendFunction0<Response<List<T>>>,
        translator: suspend (List<T>) -> Unit = {},
    ): LiveData<Result> {
        if (field.value == null || needRefresh) {
            getItems(field, invoker) { it?.apply { translator(this) } }
            needRefresh = false
        }
        return field
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

    private var needRefresh: Boolean = false
    var screen: Screen = Screen.Crew

    private val _capsules = MutableLiveData<Result>()
    val capsules: LiveData<Result> get() = collectData(_capsules, repository::getCapsules, ::translateLastUpdate)

    private val _cores = MutableLiveData<Result>()
    val cores: LiveData<Result>
        get() = Transformations.map(collectData(_cores, repository::getCores, ::translateLastUpdate)) { result ->
            result.toListOf<Core>()?.sortedWith(compareBy(Core::block, Core::serial))?.reversed()?.toSuccess() ?: result
        }

    private val _crew = MutableLiveData<Result>()
    val crew: LiveData<Result> get() = collectData(_crew, repository::getCrew)

    private val _dragons = MutableLiveData<Result>()
    val dragons: LiveData<Result> get() = collectData(_dragons, repository::getDragons, ::translateDescription)

    private val _rockets = MutableLiveData<Result>()
    val rockets: LiveData<Result> get() = collectData(_rockets, repository::getRockets, ::translateDescription)

    private val _launchPads = MutableLiveData<Result>()
    val launchPads: LiveData<Result> get() = collectData(_launchPads, repository::getLaunchPads, ::translateDetails)

    private val _landingPads = MutableLiveData<Result>()
    val landingPads: LiveData<Result> get() = collectData(_landingPads, repository::getLandingPads, ::translateDetails)

    private val _launches = MutableLiveData<Result>()
    val launches: LiveData<Result>
        get() = Transformations.map(collectData(_launches, repository::getLaunches, ::transformLaunches)) { result ->
            result.toListOf<Launch>()?.sortedByDescending { it.dateLocal }?.toSuccess() ?: result
        }

    private suspend fun transformLaunches(items: List<Launch>) = items.forEach {
        if (it.links.patch.small == null && it.rocket != null) {
            val rocketResponse = repository.getRocketById(it.rocket)
            it.links.patch.alternate = if (rocketResponse.isSuccessful)
                rocketResponse.body()!!.images[0] else null
        }
    }

    fun armRefresh() {
        needRefresh = true
    }
}