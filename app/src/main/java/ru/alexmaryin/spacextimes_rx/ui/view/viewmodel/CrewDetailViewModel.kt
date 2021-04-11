package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderApi
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.LinksItem
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.OneLineItem2
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
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
        repository.getCrewById(state.get("crewId") ?: "")
            .map { if (it is Success<*>) (it.data as Crew).wikiLocale = localeWikiUrl(it.data.wikipedia); it }
            .collect { result -> crewState.value = result }
    }

    private suspend fun localeWikiUrl(enUrl: String?) = enUrl?.let {
        wikiApi.getLocaleLink(enUrl, state.get("locale") ?: "en")
    }

    fun composeDetails(res: Context, crew: Crew) = mutableListOf<HasStringId>().apply {
        crew.agency?.let {
            add(
                OneLineItem2(
                    left = res.getString(R.string.agency_label_text),
                    right = it
                )
            )
        }
        add(RecyclerHeader(text = res.getString(R.string.missions_list_header)))
        addAll(crew.launches)

        add(RecyclerHeader(text = res.getString(R.string.links_string)))
        add(LinksItem(wiki = crew.wikiLocale ?: crew.wikipedia))
    }
}