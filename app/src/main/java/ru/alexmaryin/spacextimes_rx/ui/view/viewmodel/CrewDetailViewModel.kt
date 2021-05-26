package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.wiki.localizeWiki
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.LinksItem
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.OneLineItem2
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.RecyclerHeader
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import javax.inject.Inject

@HiltViewModel
class CrewDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    private val repository: SpacexDataRepository,
    private val settings: Settings,
) : ViewModel() {

    private val crewState = MutableStateFlow<Result>(Loading)
    fun getCrewState() = crewState.asStateFlow()

    fun loadCrew() = viewModelScope.launch {
        repository.getCrewById(state.get("crewId") ?: "")
            .localizeWiki<Crew>(state.get("locale") ?: "en")
            .collect { result -> crewState.emit(result) }
    }

    fun composeDetails(res: Context, crew: Crew) = mutableListOf<HasStringId>().apply {

        if (crew.launches.isEmpty()) {
            settings.armedSynchronize = true
            loadCrew()
        } else {
            crew.agency?.let {
                add(
                    OneLineItem2(
                        left = res.getString(R.string.agency_label_text),
                        right = it
                    )
                )
            }
            if (crew.launches.isNotEmpty()) {
                add(RecyclerHeader(text = res.getString(R.string.missions_list_header)))
                addAll(crew.launches)
            }

            add(RecyclerHeader(text = res.getString(R.string.links_string)))
            add(LinksItem(wiki = crew.wikiLocale ?: crew.wikipedia))
        }
    }
}