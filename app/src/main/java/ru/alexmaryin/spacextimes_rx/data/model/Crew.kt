package ru.alexmaryin.spacextimes_rx.data.model

import com.squareup.moshi.JsonClass
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.common.HasWiki
import ru.alexmaryin.spacextimes_rx.data.model.enums.CrewStatus
import ru.alexmaryin.spacextimes_rx.data.room_model.CrewLocal

@JsonClass(generateAdapter = true)
data class Crew(
    override val id: String,
    val name: String?,
    val status: CrewStatus,
    val agency: String?,
    val image: String?,
    override val wikipedia: String?,
    @Transient override var wikiLocale: String? = null,
    val launches: List<Launch> = emptyList()
) : HasStringId, HasWiki {

    fun toRoom() = CrewLocal(id, name, status, agency, image, wikipedia)
}