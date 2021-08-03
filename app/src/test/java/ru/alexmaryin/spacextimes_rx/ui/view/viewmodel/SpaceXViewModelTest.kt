package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.ErrorType
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SpaceXViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock private lateinit var repository: SpacexDataRepository
    @Mock private lateinit var settings: Settings
    private lateinit var translator: TranslatorApi
    private lateinit var viewModel: SpaceXViewModel
    private lateinit var closable: AutoCloseable
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        closable = MockitoAnnotations.openMocks(this)
        translator = object : TranslatorApi {
            override fun Flow<Result>.translateDetails() = this
            override fun Flow<Result>.translateLastUpdate() = this
            override fun Flow<Result>.translateDescription() = this
            override fun Flow<Result>.translateTitle() = this
            override suspend fun restoreFromBackup() = false
            override suspend fun backupTranslations() = false
        }
        viewModel = SpaceXViewModel(repository, translator)
    }

    @After
    fun tearDown() {
        closable.close()
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `viewModel should return loading first`() = testCoroutineScope.runBlockingTest {
        `when`(repository.getLaunches("Launches")).thenReturn(flowOf(Loading).stateIn(this))

        viewModel.changeScreen(Launches)
        val result = viewModel.getState().first()
        assertTrue(result == Loading)
        verify(repository).getLaunches("Launches")
    }

    @Test
    fun` viewModel should return error after loading`() = testCoroutineScope.runBlockingTest {
        val list = emptyList<Result>().toMutableList()
        val flow = flow {
            emit(Loading)
            delay(100)
            emit(Error("", ErrorType.REMOTE_API_ERROR))
        }.stateIn(this)

        `when`(repository.getLaunches("Launches")).thenReturn(flow)

        var loadingFlag = false

        viewModel.changeScreen(Launches)
        viewModel.getState().take(2).collect { state ->
            list.add(state)
            when (state) {
                is Loading -> loadingFlag = true
                is Error -> {
                    assertTrue("Error type should by REMOTE API ERROR", state.error == ErrorType.REMOTE_API_ERROR)
                    assertTrue("Error should be emitted after Loading", loadingFlag)
                }
                else -> throw TypeCastException("Unknown state")
            }
        }
        verify(repository).getLaunches("Launches")
        assertEquals(listOf(Loading, Error("", ErrorType.REMOTE_API_ERROR)), list)
    }

//    @Test
//    fun `scroll next launch should return next upcoming launch`() = testCoroutineScope.runBlockingTest {
//        val mockLaunch = prepareMockLaunch(DatePrecision.DAY)
//        val l = listOf(mockLaunch).indexOfLast { it.datePrecision >= DatePrecision.DAY && it.dateLocal > Calendar.getInstance().time }
//        println(l)
//        assertTrue(viewModel.getNextLaunchPosition(listOf(mockLaunch)) == 0)
//    }
//
//    @Test
//    fun `scroll next launch should not return next launch without day date precision`() = testCoroutineScope.runBlockingTest {
//        val mockLaunch = prepareMockLaunch(DatePrecision.MONTH)
//        val l = listOf(mockLaunch).indexOfLast { it.datePrecision >= DatePrecision.DAY && it.dateLocal > Calendar.getInstance().time }
//        println(l)
//        assertTrue(viewModel.getNextLaunchPosition(listOf(mockLaunch)) == null)
//    }
//
//    @Test
//    fun `scroll next launch should select only one with day precision`() = testCoroutineScope.runBlockingTest {
//        val mockLaunch1 = prepareMockLaunch(DatePrecision.MONTH, 12)
//        val mockLaunch2 = prepareMockLaunch(DatePrecision.DAY, 16)
//        val list = listOf(mockLaunch1, mockLaunch2)
//        println(list)
//
//        assertTrue(repository.getNextLaunch(list) == 1)
//    }

//    @Test
//    fun `scroll next launch should emit error if upcoming is turned off`() = testCoroutineScope.runBlockingTest {
//        val flow = flow { emit(Success(emptyList<Launch>())) }.stateIn(this)
//        `when`(repository.getLaunches()).thenReturn(flow)
//        viewModel.changeScreen(Launches)
//        viewModel.toggleLaunchFilter("Upcoming")
//        viewModel.getScrollTrigger().test {
//            viewModel.scrollNextLaunch()
//            assertFalse(expectItem())
//        }
//    }

//    // mock objects
//
//    private fun prepareMockLaunch(
//        precision: DatePrecision = DatePrecision.HOUR,
//        addHours: Int = 12) = mock(Launch::class.java).apply {
//        `when`(datePrecision).thenReturn(precision)
//        `when`(upcoming).thenReturn(true)
//        `when`(dateLocal).thenReturn(Calendar.getInstance().apply { add(Calendar.HOUR, addHours) }.time)
//    }
}
