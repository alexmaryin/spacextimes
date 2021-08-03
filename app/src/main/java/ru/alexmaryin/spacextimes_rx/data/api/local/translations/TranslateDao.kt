package ru.alexmaryin.spacextimes_rx.data.api.local.translations

import androidx.room.*

@Dao
interface TranslateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TranslateItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TranslateItem>)

    @Query("select * from translations where origin=:origin")
    suspend fun findString(origin: String): TranslateItem?

    @Query("select * from translations")
    suspend fun selectAll(): List<TranslateItem>

    @Query("delete from translations")
    suspend fun clear()
}