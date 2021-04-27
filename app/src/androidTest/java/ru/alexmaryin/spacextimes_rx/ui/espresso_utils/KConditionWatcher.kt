package ru.alexmaryin.spacextimes_rx.ui.espresso_utils

const val CONDITION_NOT_MET = 0
const val CONDITION_MET = 1
const val TIMEOUT = 2

const val DEFAULT_TIMEOUT_LIMIT = 1000 * 60
const val DEFAULT_INTERVAL = 250

class KConditionWatcher(
    private val timeoutLimit: Int = DEFAULT_TIMEOUT_LIMIT,
    private val watchInterval: Int = DEFAULT_INTERVAL
) {

    companion object {
        @Volatile private var instance: KConditionWatcher? = null

        fun getInstance(): KConditionWatcher = instance ?: run {
            instance = KConditionWatcher()
            return instance!!
        }
    }

    fun waitForCondition(instruction: KInstruction) {
        var status = CONDITION_NOT_MET
        var elapsedTime = 0

        while (status != CONDITION_MET) {
            if (instruction.checkCondition()) status = CONDITION_MET
                else {
                    elapsedTime += getInstance().watchInterval
                    Thread.sleep(getInstance().watchInterval.toLong())
                }

            if (elapsedTime > getInstance().timeoutLimit) {
                status = TIMEOUT
                break
            }
        }

        if (status == TIMEOUT) throw Exception("${instruction.getDescription()} - timeout (more than ${getInstance().timeoutLimit/1000}). Test stopped.")
    }

}