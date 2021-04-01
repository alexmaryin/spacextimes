package ru.alexmaryin.spacextimes_rx.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "translations")
data class TranslateItem(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val origin: String,
    val translation: String?,
    val insertDate: Date,
)
