package ru.alexmaryin.spacextimes_rx.data.api.translator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDescription
import ru.alexmaryin.spacextimes_rx.data.model.common.HasDetails
import ru.alexmaryin.spacextimes_rx.data.model.common.HasLastUpdate
import ru.alexmaryin.spacextimes_rx.data.model.common.HasTitle
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.*
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

class TranslatorImpl @Inject constructor(
    private val settings: Settings,
    private val translatorInternal: TranslatorInternalApi
) : TranslatorApi {

    private inline fun <reified T> Flow<Result>.doTranslate(
        from: KProperty1<T, String?>,
        to: KMutableProperty1<T, String?>
    ) = map { result ->
        if (result is Success<*> && settings.translateToRu) {
            result.toListOf<T>()?.let { items ->
                withContext(Dispatchers.IO) {
                    translatorInternal.tryLoadLocalTranslate(coroutineContext, items, from, to)
                    translatorInternal.translate(coroutineContext, items, from, to)
                }
                items.toSuccess()
            } ?: throw TypeCastException("List have not implement HasDetails interface")
        } else {
            result
        }
    }.catch { e ->
        if (e is IOException) emit(Error("Translator error: ${e.localizedMessage}", ErrorType.REMOTE_TRANSLATOR_ERROR))
    }

    override fun Flow<Result>.translateDetails(): Flow<Result> = doTranslate(HasDetails::details, HasDetails::detailsRu)

    override fun Flow<Result>.translateLastUpdate(): Flow<Result> = doTranslate(HasLastUpdate::lastUpdate, HasLastUpdate::lastUpdateRu)

    override fun Flow<Result>.translateDescription(): Flow<Result> = doTranslate(HasDescription::description, HasDescription::descriptionRu)

    override fun Flow<Result>.translateTitle(): Flow<Result> = doTranslate(HasTitle::title, HasTitle::titleRu)
}