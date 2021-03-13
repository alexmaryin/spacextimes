package ru.alexmaryin.spacextimes_rx.data.api.wiki

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class WikiLoaderImplTest {

   private val wikiApi = WikiLoaderImpl()

    @Test
    fun `wiki link translator should return correct link`() {
        val origin = "https://en.wikipedia.org/wiki/Bob_Behnken"
        val expected = "https://ru.wikipedia.org/wiki/%D0%91%D0%B5%D0%BD%D0%BA%D0%B5%D0%BD,_%D0%A0%D0%BE%D0%B1%D0%B5%D1%80%D1%82"
        val locale = "ru"

        assertEquals(expected, wikiApi.getLocaleLink(origin, locale))
    }

    @Test
    fun `wiki link translator should return origin link if specified is avoid`() {
        val origin = "https://en.wikipedia.org/wiki/Bob_Behnken"
        val expected = "https://en.wikipedia.org/wiki/Bob_Behnken"
        val locale = "гы"

        assertEquals(expected, wikiApi.getLocaleLink(origin, locale))
    }
}