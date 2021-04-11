package ru.alexmaryin.spacextimes_rx.data.model.ui_items

import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

data class LinksItem(
    override val id: String = "links",
    val wiki: String? = null,
    val youtube: String? = null,
    val redditCampaign: String? = null,
    val redditLaunch: String? = null,
    val redditMedia: String? = null,
    val redditRecovery: String? = null,
    val pressKit: String? = null,
    val article: String? = null,
) : HasStringId {
    val isNotEmpty get() = this != LinksItem()
}
