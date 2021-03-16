package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderApi
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject

@HiltViewModel
class DragonDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
    private val networkHelper: NetworkHelper,
    private val wikiApi: WikiLoaderApi,
) : ViewModel() {

    private var _dragonDetails = MutableLiveData<Dragon>()
    val dragonDetails: LiveData<Dragon>
        get() = _dragonDetails

    private var _dragon = MutableLiveData<Result>(Loading)
    val dragon: LiveData<Result>
        get() {
            viewModelScope.launch {
                _dragon.postValue(Loading)
                if (networkHelper.isNetworkConnected()) {
                    repository.getDragonById(state.get("dragonId") ?: "").let { response ->
                        if (response.isSuccessful) {
                            response.body()!!.wikiLocale = localeWikiUrl(response.body()!!.wikipedia)
                            _dragonDetails.postValue(response.body()!!)
                            _dragon.postValue(Success(null))
                        } else _dragon.postValue(Error(response.errorBody().toString()))
                    }
                } else _dragon.postValue(Error("No internet connection!"))
            }
            return _dragon
        }

    private suspend fun localeWikiUrl(enUrl: String) = wikiApi.getLocaleLink(enUrl, state.get("locale") ?: "en")

    fun getTitle() = dragonDetails.value?.name
}