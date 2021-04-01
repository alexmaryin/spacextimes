package ru.alexmaryin.spacextimes_rx.data.local

import androidx.room.*
import java.util.*

@Fts4
@Entity(tableName = "translations")
data class TranslateItem(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid") val rowId: Int = 0,
    val origin: String,
    val translation: String?,
    val insertDate: Date,
)
