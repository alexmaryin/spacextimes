package ru.alexmaryin.spacextimes_rx.ui.espresso_utils

import android.os.Bundle
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

object KConditionWatchers {

    private const val TIMEOUT_LIMIT = 20000
    private const val WATCH_INTERVAL = 400
    private val exceptionList: List<String>? = null

    @Throws(Exception::class)
    @JvmStatic
    fun waitForElement(interaction: ViewInteraction,
                       timeout: Int = TIMEOUT_LIMIT
    ): ViewInteraction {
        KConditionWatcher.getInstance().timeoutLimit = timeout
        KConditionWatcher.getInstance().waitForCondition(object : KInstruction {
            override val dataContainer: Bundle? get() = null

            override fun getDescription() = "waitForElement"

            override fun checkCondition() = try {
                interaction.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                true
            } catch (ex: NoMatchingViewException) {
                false
            }
        })
        return interaction
    }

    @Throws(Exception::class)
    fun waitForElementFullyVisible(
        interaction: ViewInteraction,
        timeout: Int
    ): ViewInteraction {
        KConditionWatcher.getInstance().timeoutLimit = timeout
        KConditionWatcher.getInstance().waitForCondition(object : KInstruction {
            override val dataContainer: Bundle? get() = null

            override fun getDescription() = "waitForElementFullyVisible"

            override fun checkCondition() = try {
                interaction.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                true
            } catch (ex: NoMatchingViewException) {
                false
            }
        })
        return interaction
    }

    @Throws(Exception::class)
    @JvmStatic
    fun waitForElementIsGone(
        interaction: ViewInteraction,
        timeout: Int = TIMEOUT_LIMIT
    ): ViewInteraction {
        KConditionWatcher.getInstance().timeoutLimit = timeout
        KConditionWatcher.getInstance().waitForCondition(object : KInstruction {
            override val dataContainer: Bundle? get() = null

            override fun getDescription() = "waitForElementIsGone"

            override fun checkCondition() = try {
                interaction.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                false
            } catch (ex: NoMatchingViewException) {
                true
            }
        })
        return interaction
    }

}