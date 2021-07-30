package ru.alexmaryin.spacextimes_rx.data.api.local.translations

import androidx.room.*
import com.squareup.moshi.JsonClass
import java.util.*

@Entity(tableName = "translations", indices = [
    Index(name = "origin_idx", value = ["origin"], unique = true)
])
data class TranslateItem(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid") val rowId: Int? = null,
    val origin: String,
    val translation: String?,
    val insertDate: Date,
)

@JsonClass(generateAdapter = true)
data class TranslateAssetItem(
    val origin: String,
    val translation: String
)

@JsonClass(generateAdapter = true)
data class TranslateAsset(
    val version: Int,
    val items: List<TranslateAssetItem>
)
