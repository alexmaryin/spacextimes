package ru.alexmaryin.spacextimes_rx.data.repository

//import androidx.room.Room
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.platform.app.InstrumentationRegistry
//import org.junit.After
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.io.IOException
//import java.util.*@RunWith(AndroidJUnit4::class)
//class TranslateDatabaseTest {
//
//    private lateinit var dao: TranslateDao
//    private lateinit var db: TranslateDatabase
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        db = Room.inMemoryDatabaseBuilder(context, TranslateDatabase::class.java)
//            .allowMainThreadQueries()
//            .build()
//        dao = db.dao
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun `database should insert and get back same item`() {
//        val testStr = "Translate me, please!"
//        val item = TranslateItem(testStr.hashCode(), testStr, testStr, Date())
//        dao.insert(item)
//        val restoredItem =
//
//            dao.get(testStr.hashCode())
//        println("String with id=${testStr.hashCode()} and text $testStr")
//        assertEquals(restoredItem?.origin, testStr)
//    }
//}
