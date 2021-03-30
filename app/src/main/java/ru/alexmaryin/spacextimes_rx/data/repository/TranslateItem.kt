package ru.alexmaryin.spacextimes_rx.data.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "translations_table")
data class TranslateItem(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val origin: String,
    val translation: String?,
    val insertDate: Date,
)
