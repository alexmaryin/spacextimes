package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import ru.alexmaryin.spacextimes_rx.data.api.ApiImpl
import ru.alexmaryin.spacextimes_rx.data.api.ApiService
import ru.alexmaryin.spacextimes_rx.data.api.SpacexUrls
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApiImpl
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleType
import java.util.*

class SpaceXViewModelTest {

    private val retrofit = Retrofit.Builder()
        .baseUrl(SpacexUrls.Base)
        .client(OkHttpClient())
        .build()

    private val translatorApi = TranslatorApiImpl(ApiImpl(retrofit.create(ApiService::class.java)))

    private fun testCapsulesList(isNullInLastUpdate: Boolean) = mutableListOf<Capsule>().apply {
        (0..9).forEach {
            add(
                Capsule(
                    serial = "10$it",
                    lastUpdate = when (isNullInLastUpdate) {
                        true -> null
                        false -> "Test of translator"
                    },
                    type = CapsuleType.values().random(),
                    status = CapsuleStatus.values().random(),
                    id = "373h3gh3636edetgey33",
                    reuseCount = Random().nextInt(10),
                    waterLandings = Random().nextInt(10),
                    landLandings = 0,
                    lastUpdateRu = null
                )
            )
        }
    }

    @Test
    fun translateCapsulesLastUpdateInitialNull() = runBlocking {
        val testList = testCapsulesList(true)
        testList.filter { it.lastUpdate != null }.forEach {
            throw RuntimeException("This element ${it.serial} should be null!!")
        }
    }

//    @Test
//    fun translateCapsulesLastUpdateNotEmpty() = runBlocking {
//        val testList = testCapsulesList(false)
//        translatorApi.fromList(testList, { it.lastUpdate!! }, {c, s -> c.lastUpdateRu = s })
//        testList.filter { it.lastUpdateRu != null }.forEach {
//            assertEquals("Тест переводчика", it.lastUpdateRu)
//        }
//    }
}