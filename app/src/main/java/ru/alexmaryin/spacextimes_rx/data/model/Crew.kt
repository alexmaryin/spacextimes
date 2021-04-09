package ru.alexmaryin.spacextimes_rx.data.model

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.enums.CrewStatus
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches

data class Crew(
    override val id: String,
    val name: String,
    val status: CrewStatus,
    val agency: String?,
    val image: String,
    override val wikipedia: String?,
    override var wikiLocale: String?,
    val launches: List<Launches> = emptyList()
) : HasStringId, HasWiki