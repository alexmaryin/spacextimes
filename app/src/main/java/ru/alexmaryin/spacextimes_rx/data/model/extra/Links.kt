package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    val patch: PatchImages,
    val reddit: Reddit,
    val flickr: FlickrImages,
    val presskit: String?,
    val webcast: String?,
    val article: String?,
    val wikipedia: String?,
    @Transient var wikiLocale: String? = null,
    @Json(name = "youtube_id") val youtubeId: String?,
)