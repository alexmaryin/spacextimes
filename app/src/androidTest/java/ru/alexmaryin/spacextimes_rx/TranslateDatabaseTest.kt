package ru.alexmaryin.spacextimes_rx

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.alexmaryin.spacextimes_rx.data.repository.TranslateDao
import ru.alexmaryin.spacextimes_rx.data.repository.TranslateDatabase
import ru.alexmaryin.spacextimes_rx.data.repository.TranslateItem
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class TranslateDatabaseTest {

    private lateinit var dao: TranslateDao
    private lateinit var db: TranslateDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, TranslateDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        dao = db.dao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun room_should_save_and_restore_identical_strings_by_hashcode() {
        val testStr = "Translate me, please!"
        val item = TranslateItem(testStr.hashCode(), testStr, testStr, Date())
        dao.insert(item)
        print("Saved item with id=${item.id}; ")
        val restoredItem = dao.get(testStr.hashCode())
        println("restored item with id=${restoredItem?.id}")
        assertEquals(testStr, restoredItem?.translation)
    }
}