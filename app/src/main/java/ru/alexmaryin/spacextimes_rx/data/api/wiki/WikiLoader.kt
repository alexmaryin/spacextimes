package ru.alexmaryin.spacextimes_rx.data.api.wiki

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success

const val WIKI_INTERLANGUAGE_SELECTOR = ".interlanguage-link.interwiki"

@Suppress("BlockingMethodInNonBlockingContext")
suspend inline fun <reified T: HasWiki> Flow<Result>.localizeWiki(locale: String): Flow<Result> = map { result ->
    if (result is Success<*> && result.data is T) {
        with (result.data) {
            wikipedia?.let { origin ->
                withContext(Dispatchers.IO) {
                    val html = Jsoup.connect(origin).get()
                    val link = html.selectFirst("$WIKI_INTERLANGUAGE_SELECTOR-$locale")
                    wikiLocale = link?.getElementsByTag("a")?.attr("href") ?: origin
                }
            }
            Success(this)
        }
    } else {
        result
    }
}