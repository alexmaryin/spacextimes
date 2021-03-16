package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderApi
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.*
import javax.inject.Inject

@HiltViewModel
class CrewDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
    private val networkHelper: NetworkHelper,
    private val wikiApi: WikiLoaderApi,
) : ViewModel() {

    private var _crewDetails = MutableLiveData<Crew>()
    val crewDetails: LiveData<Crew>
        get() = _crewDetails

    private var _crew = MutableLiveData<Result>(Loading)
    val crew: LiveData<Result>
        get() {
            viewModelScope.launch {
                _crew.postValue(Loading)
                if (networkHelper.isNetworkConnected()) {
                    repository.getCrewById(state.get("crewId") ?: "").let { response ->
                        if (response.isSuccessful) {
                            response.body()!!.wikiLocale = localeWikiUrl(response.body()!!.wikipedia)
                            _crewDetails.postValue(response.body()!!)
                            _crew.postValue(Success(null))
                        } else _crew.postValue(Error(response.errorBody().toString()))
                    }
                } else _crew.postValue(Error("No internet connection!"))
            }
            return _crew
        }

    private suspend fun localeWikiUrl(enUrl: String) = wikiApi.getLocaleLink(enUrl, state.get("locale") ?: "en")

    fun getTitle() = crewDetails.value?.name
}