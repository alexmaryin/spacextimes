package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderApi
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import javax.inject.Inject

@HiltViewModel
class CrewDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
    private val wikiApi: WikiLoaderApi,
) : ViewModel() {

    private val _crewDetails = MutableLiveData<Crew>()
    val crewDetails: LiveData<Crew> get() = _crewDetails

    val crew: LiveData<Result>
        get() =
            repository.getCrewById(state.get("crewId") ?: "").map {
                when (it) {
                    is Success<*> -> {
                        _crewDetails.postValue(it.data as Crew)
                        it.apply { (data as Crew).wikiLocale = localeWikiUrl(data.wikipedia) }
                    }
                    else -> it
                }
            }.asLiveData(viewModelScope.coroutineContext)

private suspend fun localeWikiUrl(enUrl: String?) = enUrl?.let {
    wikiApi.getLocaleLink(enUrl, state.get("locale") ?: "en")
}

fun getTitle() = crewDetails.value?.name
}