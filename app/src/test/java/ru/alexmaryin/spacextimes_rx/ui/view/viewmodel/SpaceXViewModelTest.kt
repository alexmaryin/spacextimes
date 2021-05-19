package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import kotlinx.coroutines.*
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
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.data.model.lists.Launches
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.*
import java.util.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SpaceXViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock private lateinit var repository: SpacexDataRepository
    private lateinit var translator: TranslatorApi
    private lateinit var viewModel: SpaceXViewModel
    private lateinit var closable: AutoCloseable
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
    private val settings = Settings()

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        closable = MockitoAnnotations.openMocks(this)
        translator = object : TranslatorApi {
            override fun Flow<Result>.translateDetails() = this
            override fun Flow<Result>.translateLastUpdate() = this
            override fun Flow<Result>.translateDescription() = this
            override fun Flow<Result>.translateTitle() = this
        }
        viewModel = SpaceXViewModel(settings, repository, translator)
    }

    @After
    fun tearDown() {
        closable.close()
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `viewModel should return loading first`() = testCoroutineScope.runBlockingTest {
        `when`(repository.getLaunches()).thenReturn(flowOf(Loading).stateIn(this))

        viewModel.changeScreen(LaunchesScr)
        val result = viewModel.getState().first()
        assertTrue(result == Loading)
        verify(repository).getLaunches()
    }

    @Test
    fun` viewModel should return error after loading`() = testCoroutineScope.runBlockingTest {
        val list = emptyList<Result>().toMutableList()
        val flow = flow {
            emit(Loading)
            delay(100)
            emit(Error("", ErrorType.REMOTE_API_ERROR))
        }.stateIn(this)

        `when`(repository.getLaunches()).thenReturn(flow)

        var loadingFlag = false

        viewModel.changeScreen(LaunchesScr)
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
        verify(repository).getLaunches()
        assertEquals(listOf(Loading, Error("", ErrorType.REMOTE_API_ERROR)), list)
    }

    @Test
    fun `scroll next launch should return next upcoming launch`() = testCoroutineScope.runBlockingTest {
        val mockLaunch = prepareMockLaunch(DatePrecision.DAY)
        settings.currentListMap += LaunchesScr.name to listOf(mockLaunch)

        viewModel.getScrollTrigger().test {
            viewModel.scrollNextLaunch()
            assertTrue(expectItem() is Success<*>)
        }
    }

    @Test
    fun `scroll next launch should not return next launch without day date precision`() = testCoroutineScope.runBlockingTest {
        val mockLaunch = prepareMockLaunch(DatePrecision.MONTH)
        settings.currentListMap += LaunchesScr.name to listOf(mockLaunch)

        viewModel.getScrollTrigger().test {
            viewModel.scrollNextLaunch()
            expectNoEvents()
        }
    }

    @Test
    fun `scroll next launch should select only one with day precision`() = testCoroutineScope.runBlockingTest {
        val mockLaunch1 = prepareMockLaunch(DatePrecision.MONTH, 12)
        val mockLaunch2 = prepareMockLaunch(DatePrecision.DAY, 16)
        settings.currentListMap += LaunchesScr.name to listOf(mockLaunch1, mockLaunch2)

        viewModel.getScrollTrigger().test {
            viewModel.scrollNextLaunch()
            expectItem().apply {
                assertTrue(this is Success<*>)
                assertTrue(toDetails<Pair<Int, Launches>>().first == 1)
            }
        }
    }

    @Test
    fun `scroll next launch should emit error if upcoming is turned off`() = testCoroutineScope.runBlockingTest {
        val mockLaunch = prepareMockLaunch(DatePrecision.DAY)
        settings.currentListMap += LaunchesScr.name to listOf(mockLaunch)
        viewModel.toggleLaunchFilter(LaunchFilter.Upcoming)

        viewModel.getScrollTrigger().test {
            viewModel.scrollNextLaunch()
            assertTrue(expectItem() == Error("", ErrorType.UPCOMING_LAUNCHES_DESELECTED))
        }
    }

    // mock objects

    private fun prepareMockLaunch(
        precision: DatePrecision = DatePrecision.HOUR,
        addHours: Int = 12) = mock(Launches::class.java).apply {
        `when`(datePrecision).thenReturn(precision)
        `when`(upcoming).thenReturn(false)
        `when`(dateLocal).thenReturn(Calendar.getInstance().apply { add(Calendar.HOUR, addHours) }.time)
    }
}
