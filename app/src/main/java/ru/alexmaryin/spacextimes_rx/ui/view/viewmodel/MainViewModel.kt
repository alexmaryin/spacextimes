package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.toListOf
import ru.alexmaryin.spacextimes_rx.utils.toSuccess
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    private val repository: SpacexDataRepository,
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

    val capsules: LiveData<Result> get() = repository.getCapsules { it?.let { translateLastUpdate(it) } }
        .asLiveData(viewModelScope.coroutineContext)

    val cores: LiveData<Result> get() = repository.getCores { it?.let { translateLastUpdate(it) } }.map {
                it.toListOf<Core>()?.sortedWith(compareBy(Core::block, Core::serial))?.reversed()?.toSuccess() ?: it
            }.asLiveData(viewModelScope.coroutineContext)

    val crew: LiveData<Result> get() = repository.getCrew().asLiveData(viewModelScope.coroutineContext)

    val dragons: LiveData<Result> get() = repository.getDragons { it?.let { translateDescription(it) } }
        .asLiveData(viewModelScope.coroutineContext)

    val rockets: LiveData<Result> get() = repository.getRockets { it?.let { translateDescription(it) } }
        .asLiveData(viewModelScope.coroutineContext)

    val launchPads: LiveData<Result> get() = repository.getLaunchPads { it?.let { translateDetails(it) } }
        .asLiveData(viewModelScope.coroutineContext)

    val landingPads: LiveData<Result> get() = repository.getLandingPads { it?.let { translateDetails(it) } }
        .asLiveData(viewModelScope.coroutineContext)

    val launches: LiveData<Result> get() = repository.getLaunches().asLiveData(viewModelScope.coroutineContext)

    fun armRefresh() {
        needRefresh = true
    }
}