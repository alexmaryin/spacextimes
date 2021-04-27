package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.di.Settings
import ru.alexmaryin.spacextimes_rx.utils.Error
import ru.alexmaryin.spacextimes_rx.utils.ErrorType
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SpaceXViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var translatorApi: TranslatorApi

    @Mock
    private lateinit var repository: SpacexDataRepository
    private lateinit var viewModel: SpaceXViewModel

    private lateinit var closable: AutoCloseable
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        closable = MockitoAnnotations.openMocks(this)
        viewModel = SpaceXViewModel(repository, Settings(), translatorApi)
    }

    @After
    fun tearDown() {
        closable.close()
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `viewModel should return loading first`() = testCoroutineScope.runBlockingTest {
        `when`(repository.getLaunches(listOf(translatorApi::translateDetails))).thenReturn(flowOf(Loading).stateIn(this))

        viewModel.changeScreen(Screen.Launches)
        val result = viewModel.getState().first()
        assertTrue(result == Loading)
        verify(repository).getLaunches(listOf(translatorApi::translateDetails))
    }

    @Test
    fun` viewModel should return error after loading`() = testCoroutineScope.runBlockingTest {

        val list = emptyList<Result>().toMutableList()

        val flow = flow {
            emit(Loading)
            delay(100)
            emit(Error("", ErrorType.REMOTE_API_ERROR))
        }.stateIn(this)

        `when`(repository.getLaunches(listOf(translatorApi::translateDetails))).thenReturn(flow)


        var loadingFlag = false

        viewModel.changeScreen(Screen.Launches)
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
        verify(repository).getLaunches(listOf(translatorApi::translateDetails))
        assertEquals(listOf(Loading, Error("", ErrorType.REMOTE_API_ERROR)), list)
    }
}