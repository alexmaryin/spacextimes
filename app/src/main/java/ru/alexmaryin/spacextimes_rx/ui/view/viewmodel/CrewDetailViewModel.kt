package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.di.module.Settings

class CrewDetailViewModel @ViewModelInject constructor(
    private val settings: Settings,
    private val translator: TranslatorApi
) : ViewModel() {



}