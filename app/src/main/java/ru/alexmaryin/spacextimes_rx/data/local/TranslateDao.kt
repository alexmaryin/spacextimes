package ru.alexmaryin.spacextimes_rx.data.local

import androidx.room.*

@Dao
interface TranslateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranslateItem)

    @Update
    suspend fun update(item: TranslateItem)

    @Query("select *, rowid from translations where origin=:origin")
    suspend fun findString(origin: String): TranslateItem?

    @Query("delete from translations")
    suspend fun clear()
}