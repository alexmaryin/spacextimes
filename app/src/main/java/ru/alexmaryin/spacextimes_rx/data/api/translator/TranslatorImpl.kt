package ru.alexmaryin.spacextimes_rx.data.api.translator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasTitle
import ru.alexmaryin.spacextimes_rx.di.Settings
import javax.inject.Inject

class TranslatorImpl @Inject constructor(
    private val settings: Settings,
    private val translatorInternal: TranslatorInternalApi
) : TranslatorApi {
    override suspend fun translateDetails(items: List<HasDetails>?) {
        if(settings.translateToRu && !items.isNullOrEmpty()) {
            withContext(Dispatchers.IO) {
                translatorInternal.tryLoadLocalTranslate(coroutineContext, items, HasDetails::details, HasDetails::detailsRu)
                translatorInternal.translate(coroutineContext, items, HasDetails::details, HasDetails::detailsRu)
            }
        }
    }

    override suspend fun translateLastUpdate(items: List<HasLastUpdate>?) {
        if(settings.translateToRu && !items.isNullOrEmpty()) {
            withContext(Dispatchers.IO) {
                translatorInternal.tryLoadLocalTranslate(coroutineContext, items, HasLastUpdate::lastUpdate, HasLastUpdate::lastUpdateRu)
                translatorInternal.translate(coroutineContext, items, HasLastUpdate::lastUpdate, HasLastUpdate::lastUpdateRu)
            }
        }
    }

    override suspend fun translateDescription(items: List<HasDescription>?) {
        if(settings.translateToRu && !items.isNullOrEmpty()) {
            withContext(Dispatchers.IO) {
                translatorInternal.tryLoadLocalTranslate(coroutineContext, items, HasDescription::description, HasDescription::descriptionRu)
                translatorInternal.translate(coroutineContext, items, HasDescription::description, HasDescription::descriptionRu)
            }
        }
    }

    override suspend fun translateTitle(items: List<HasTitle>?) {
        if(settings.translateToRu && !items.isNullOrEmpty()) {
            withContext(Dispatchers.IO) {
                translatorInternal.tryLoadLocalTranslate(coroutineContext, items, HasTitle::title, HasTitle::titleRu)
                translatorInternal.translate(coroutineContext, items, HasTitle::title, HasTitle::titleRu)
            }
        }
    }
}