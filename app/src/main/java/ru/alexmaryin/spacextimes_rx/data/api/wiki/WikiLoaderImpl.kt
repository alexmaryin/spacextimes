package ru.alexmaryin.spacextimes_rx.data.api.wiki

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

const val WIKI_INTERLANGUAGE_SELECTOR = ".interlanguage-link.interwiki"

class WikiLoaderImpl @Inject constructor() : WikiLoaderApi {
    override suspend fun getLocaleLink(origin: String, locale: String): String {
        return withContext(Dispatchers.IO) {
            val html = Jsoup.connect(origin).get()
            val link = html.selectFirst("$WIKI_INTERLANGUAGE_SELECTOR-$locale")
            link?.getElementsByTag("a")?.attr("href") ?: origin
        }
    }
}