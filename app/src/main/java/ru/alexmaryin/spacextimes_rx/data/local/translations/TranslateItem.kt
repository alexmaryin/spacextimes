package ru.alexmaryin.spacextimes_rx.data.local.translations

import androidx.room.*
import java.util.*

@Entity(tableName = "translations", indices = [
    Index(name = "origin_idx", value = ["origin"], unique = true)
])
data class TranslateItem(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid") val rowId: Int = 0,
    val origin: String,
    val translation: String?,
    val insertDate: Date,
)
