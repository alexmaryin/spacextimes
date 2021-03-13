package ru.alexmaryin.spacextimes_rx.data.api.wiki

/*
* Changes origin link of wiki page to current language link
*
* @param origin is page link
* @param locale is two-letters locale i.e. ru, en, fr, etc.
* @return generated link for specified language or origin link in case of avoid
* */
interface WikiLoaderApi {
    suspend fun getLocaleLink(origin: String, locale: String): String
}