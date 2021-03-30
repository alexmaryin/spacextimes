package ru.alexmaryin.spacextimes_rx.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TranslateDao {
    @Insert
    fun insert(item: TranslateItem)

    @Update
    fun update(item: TranslateItem)

    @Query("select * from translations_table where id=:key")
    fun get(key: Int): TranslateItem?

    @Query("delete from translations_table")
    fun clear()
}