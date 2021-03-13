package ru.alexmaryin.spacextimes_rx.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailsViewModelFactory(private val detailsId: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailsViewModel(detailsId) as T
}

open class DetailsViewModel(private val detailsId: String) : ViewModel()