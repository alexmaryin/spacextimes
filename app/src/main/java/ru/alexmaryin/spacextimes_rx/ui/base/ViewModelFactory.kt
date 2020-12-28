package ru.alexmaryin.spacextimes_rx.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.alexmaryin.spacextimes_rx.data.api.Api
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository

class ViewModelFactory(private val api: Api): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(SpacexDataRepository::class.java).newInstance(SpacexDataRepository(api))
        } catch (e: Exception) {
            throw NoSuchMethodException ("ViewModel class have no SpaceXRepo parameter in constructor")
        }
    }
}