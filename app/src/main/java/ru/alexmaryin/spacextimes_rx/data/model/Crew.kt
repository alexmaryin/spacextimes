package ru.alexmaryin.spacextimes_rx.data.model

import com.google.gson.annotations.SerializedName

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

enum class CrewStatus {
    @SerializedName("active") ACTIVE,
    @SerializedName("inactive") INACTIVE,
    @SerializedName("retired") RETIRED,
    @SerializedName("unknown") UNKNOWN
}