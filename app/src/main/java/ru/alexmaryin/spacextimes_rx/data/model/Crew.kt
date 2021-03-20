package ru.alexmaryin.spacextimes_rx.data.model

import ru.alexmaryin.spacextimes_rx.data.model.enums.CrewStatus

data class Crew(
    val id: String,
    val name: String,
    val status: CrewStatus,
    val agency: String,
    val image: String,
    val wikipedia: String,
    var wikiLocale: String?,
    val launches: List<String> = emptyList()
)