package ru.alexmaryin.spacextimes_rx.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.SmallTest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class TranslateDatabaseTest {

    @Module
    @InstallIn(SingletonComponent::class)
    object TestModule {
        @Provides
        @Named("testDb")
        fun provideInMemoryDb(@ApplicationContext context: Context) =
            Room.inMemoryDatabaseBuilder(context, TranslateDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var taskExecutor = InstantTaskExecutorRule()

    @Inject @Named("testDb") lateinit var db: TranslateDatabase
    private lateinit var dao: TranslateDao


    @Before
    fun createDb() {
        hiltRule.inject()
        dao = db.dao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun room_should_save_and_restore_identical_strings_by_hashcode() = runBlocking {
        val testStr = "Translate me, please!"
        val item = TranslateItem(origin = testStr, translation = testStr, insertDate = Date())
        dao.insert(item)
        val restoredItem = dao.findString(testStr)
        assertEquals(testStr, restoredItem?.translation)
    }
}