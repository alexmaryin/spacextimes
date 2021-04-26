package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.util.collections.Iterables.firstOf
import ru.alexmaryin.spacextimes_rx.*
import ru.alexmaryin.spacextimes_rx.databinding.LaunchItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
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
       // activityRule.scenario.close()
    }

    @Test
    fun application_should_be_first_opened_with_shimmer() {
        onView(withId(R.id.shimmerLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun launches_screen_should_be_first_opened() {
        Thread.sleep(2000)
        onView(withText(R.string.launchesTitle)).check(matches(isDisplayed()))
        onView(
            withBindingAdapter(LaunchItemBinding::class)
            .perform(RecyclerViewActions.scrollToPosition<BaseListAdapter.DataViewHolder>(0))
        onView(allOf(withId(R.id.launchName), withEffectiveVisibility(Visibility.VISIBLE))).check(matches(isDisplayed()))
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

    @Test
    fun click_on_launch_name_should_invoke_details_fragment() {
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BaseListAdapter.DataViewHolder>(0, clickItemWithId(R.id.launchName)))
        Thread.sleep(2000)
        onView(withText(R.string.launch_number_caption)).check(matches(isDisplayed()))

    }

    @Test
    fun longClick_on_patch_should_create_download_dialog() {
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BaseListAdapter.DataViewHolder>(0, longClickItemWithId(R.id.patchImage)))
        onView(withText(R.string.save_image_dialog_string)).check(matches(isDisplayed()))
        onView(withText(R.string.cancelText)).perform(click())
    }

    @Test
    fun launch_details_fragment_should_invoke_launches_list_on_press_back() {
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BaseListAdapter.DataViewHolder>(0, click()))
        Thread.sleep(2000)
        pressBack()
        onView(withText(R.string.launchesTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun history_from_menu_should_populate_recycler_by_events() {
        openContextualActionModeOverflowMenu()
        onView(withText(R.string.historyEventsTitle)).perform(click())
        Thread.sleep(2000)
        onView(withText(R.string.historyEventsTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToPosition<BaseListAdapter.DataViewHolder>(0))
        withId(R.id.eventDescription).matches(isDisplayed())
    }
}