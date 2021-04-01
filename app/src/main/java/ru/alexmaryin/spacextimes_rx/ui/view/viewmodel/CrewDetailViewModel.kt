package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderApi
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import javax.inject.Inject

@HiltViewModel
class CrewDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
    private val wikiApi: WikiLoaderApi,
) : ViewModel() {

    private val crewState = MutableStateFlow<Result>(Loading)
    fun getCrewState() = crewState.asStateFlow()

    fun loadCrew() = viewModelScope.launch {
        repository.getCrewById(state.get("crewId") ?: "").apply {
            if (this is Success<*>) (data as Crew).wikiLocale = localeWikiUrl(data.wikipedia)
        }.collect { result -> crewState.value = result }
    }

    private suspend fun localeWikiUrl(enUrl: String?) = enUrl?.let {
        wikiApi.getLocaleLink(enUrl, state.get("locale") ?: "en")
    }

}