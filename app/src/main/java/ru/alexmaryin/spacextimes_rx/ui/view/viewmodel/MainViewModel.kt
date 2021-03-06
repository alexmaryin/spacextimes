package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.ui.view.filters.EmptyFilter
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    private val repository: SpacexDataRepository,
    private val translator: TranslatorApi,
) : ViewModel() {

    var currentScreen: MainScreen = Launches
    private var needRefresh = true

    val getFilterIfAvailable get() = if (isFilterAvailable) currentScreen.filter else null

    val isFilterAvailable get() = currentScreen.filter != EmptyFilter

    val isSearchable get() = currentScreen.searchable

    private val state = MutableStateFlow<Result>(Loading)
    fun getState() = state.asStateFlow()

    private val scrollTrigger = MutableSharedFlow<Boolean>(0)
    fun getScrollTrigger() = scrollTrigger.asSharedFlow()

    fun changeScreen(screen: MainScreen) {
        if (screen != currentScreen || needRefresh) {
            currentScreen = screen
            needRefresh = false
            viewModelScope.launch {
                screen.readRepository(repository, translator).collect { result ->
                    state.emit(result)
                }
            }
        }
    }

    fun armRefresh() {
        needRefresh = true
        changeScreen(currentScreen)
    }

    fun getScrollPosition(context: Context, items: List<HasStringId>) = currentScreen.getPositionToScroll(context, items)

    fun scrollToPosition(predicate: () -> Boolean) = viewModelScope.launch { scrollTrigger.emit(predicate()) }

    fun <T> filterOrSearch(items: List<T>, text: String = ""): List<T> {
        currentScreen.filter.searchString = text
        return currentScreen.getFilteredList(items)
    }
}