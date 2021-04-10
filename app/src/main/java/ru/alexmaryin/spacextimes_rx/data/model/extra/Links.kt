package ru.alexmaryin.spacextimes_rx.data.model.extra

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.alexmaryin.spacextimes_rx.data.model.extra.FlickrImages
import ru.alexmaryin.spacextimes_rx.data.model.extra.PatchImages
import ru.alexmaryin.spacextimes_rx.data.model.extra.RedditImages

data class Links(
    val patch: PatchImages,
    val reddit: RedditImages,
    val flickr: FlickrImages,
    val presskit: String?,
    val webcast: String?,
    val article: String?,
    val wikipedia: String?,
    @Expose var wikiLocale: String?,
    @SerializedName("youtube_id") val youtubeId: String?,
)