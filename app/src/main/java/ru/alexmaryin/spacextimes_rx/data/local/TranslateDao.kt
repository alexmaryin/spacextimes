package ru.alexmaryin.spacextimes_rx.data.local

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

    @Query("select * from translations where id=:key")
    fun get(key: Int): TranslateItem?

    @Query("delete from translations")
    fun clear()
}