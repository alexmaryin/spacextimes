package ru.alexmaryin.spacextimes_rx.data.api.translator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.data.api.Api
import javax.inject.Inject

class TranslatorApiImpl @Inject constructor(val api: Api) : TranslatorApi {

    private var result = ""

    override fun translate(source: String): String {
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.translate(source)
            result = if (response.isSuccessful) {
                (response.body() as PlainTextResponse).data
            } else source
        }
        return result
    }
}