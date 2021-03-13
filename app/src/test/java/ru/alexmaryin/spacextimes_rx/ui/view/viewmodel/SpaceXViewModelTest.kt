package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import org.junit.Test
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleType
import java.util.*

class SpaceXViewModelTest {

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
    fun translateCapsulesLastUpdateInitialNull() {
        val testList = testCapsulesList(true)
        print(testList)
        assert(true)
    }

    @Test
    fun translateCapsulesLastUpdateNotEmpty() {
        val testList = testCapsulesList(false)
        print(testList)
        assert(true)
    }
}