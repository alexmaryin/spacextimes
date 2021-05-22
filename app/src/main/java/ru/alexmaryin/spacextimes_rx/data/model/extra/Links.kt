package ru.alexmaryin.spacextimes_rx.data.model.extra

import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    @Embedded val patch: PatchImages?,
    @Embedded val reddit: Reddit?,
    @Embedded val flickr: FlickrImages,
    val presskit: String? = null,
    val webcast: String? = null,
    val article: String? = null,
    val wikipedia: String? = null,
    var wikiLocale: String? = null,
    @Json(name = "youtube_id") val youtubeId: String? = null,
)