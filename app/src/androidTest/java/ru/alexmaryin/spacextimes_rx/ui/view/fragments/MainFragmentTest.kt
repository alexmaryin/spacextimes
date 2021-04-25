package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.ui.view.activities.MainActivity

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var taskExecutor = InstantTaskExecutorRule()

//    private val context = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
        activityRule.scenario.close()
    }

    @Test
    fun application_should_be_first_opened_with_shimmer() {
        onView(withId(R.id.shimmerLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun launches_screen_should_be_first_opened() {
        Thread.sleep(2000)
        onView(withText(R.string.launchesTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun filter_action_should_be_present_on_first_opened_screen() {

        onView(withId(R.id.filterAction)).check(matches(isDisplayed()))
        onView(withId(R.id.filterAction)).check(matches(isFocusable()))
        onView(withId(R.id.filterAction)).check(matches(isClickable()))

        onView(withId(R.id.filterAction)).perform(click())
        onView(withId(R.id.filterGroup)).check(matches(isDisplayed()))

        onView(withId(R.id.filterAction)).perform(click())
        onView(withId(R.id.filterGroup)).check(matches(not(isDisplayed())))
    }

    @Test
    fun filter_action_should_hide_on_any_other_screen() {
        openContextualActionModeOverflowMenu()
        onView(withText(R.string.coresTitle)).perform(click())

        Thread.sleep(2000)
        onView(withId(R.id.filterAction)).check(doesNotExist())
    }
}