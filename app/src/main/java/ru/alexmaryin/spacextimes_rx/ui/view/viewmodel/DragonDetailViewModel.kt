package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderApi
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import javax.inject.Inject

@HiltViewModel
class DragonDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
    private val wikiApi: WikiLoaderApi,
) : ViewModel() {

    val thrustersLines = arrayOf("line_1", "line_2")

    private var _dragonDetails = MutableLiveData<Dragon>()
    val dragonDetails: LiveData<Dragon> get() = _dragonDetails

    val dragon: LiveData<Result>
        get() = repository.getDragonById(state.get("dragonId") ?: "").map {
            when (it) {
                is Success<*> -> {
                    _dragonDetails.postValue(it.data as Dragon)
                    it.apply { (data as Dragon).wikiLocale = localeWikiUrl(data.wikipedia) }
                }
                else -> it
            }
        }.asLiveData(viewModelScope.coroutineContext)

    private suspend fun localeWikiUrl(enUrl: String?) = enUrl?.let {
        wikiApi.getLocaleLink(enUrl, state.get("locale") ?: "en")
    }

    fun getTitle() = dragonDetails.value?.name

    fun thrustersMap(res: Context) = dragonDetails.value?.thrusters?.map {
        mapOf(
            thrustersLines[0] to res.getString(R.string.capsule_thruster_line1, it.amount, it.pods, it.type, it.thrust.kN, it.isp),
            thrustersLines[1] to res.getString(R.string.capsule_thruster_line2, it.HotComponent, it.OxidizerComponent)
        )
    } ?: emptyList()
}