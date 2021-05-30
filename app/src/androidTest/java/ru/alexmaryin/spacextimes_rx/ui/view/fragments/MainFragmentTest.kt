package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.databinding.*
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.espresso_utils.*
import ru.alexmaryin.spacextimes_rx.ui.view.activities.MainActivity

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @get:Rule var activityRule = ActivityScenarioRule(MainActivity::class.java)
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @get:Rule var taskExecutor = InstantTaskExecutorRule()

//    private val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun application_should_be_first_opened_with_shimmer() {
        onView(withId(R.id.shimmerLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun launches_screen_should_be_first_opened() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withBindingAdapter(LaunchItemBinding::class)).atPosition(0))
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
        KConditionWatchers.waitForElementIsGone(onView(withId(R.id.filterAction))).check(doesNotExist())
    }

    @Test
    fun click_on_launch_name_should_invoke_details_fragment() {
        KConditionWatchers.waitForElement(onView(withText(R.string.launchesTitle))).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BaseListAdapter.DataViewHolder>(0, clickItemWithId(R.id.launchName)))
        KConditionWatchers.waitForElement(onView(withText(R.string.launch_number_caption))).check(matches(isDisplayed()))
    }

    @Test
    fun longClick_on_patch_should_create_download_dialog() {
        KConditionWatchers.waitForElement(onView(withText(R.string.launchesTitle))).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BaseListAdapter.DataViewHolder>(0, longClickItemWithId(R.id.patchImage)))
        onView(withText(R.string.save_image_dialog_string)).check(matches(isDisplayed()))
        onView(withText(R.string.cancelText)).perform(click())
    }

    @Test
    fun launch_details_fragment_should_invoke_launches_list_on_press_back() {
        KConditionWatchers.waitForElement(onView(withText(R.string.launchesTitle))).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<BaseListAdapter.DataViewHolder>(0, click()))
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launch_number_caption)), DEFAULT_TIMEOUT_LIMIT)
        pressBack()
        KConditionWatchers.waitForElement(onView(withText(R.string.launchesTitle))).check(matches(isDisplayed()))
    }

    @Test
    fun history_from_menu_should_populate_recycler_by_events() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.historyEventsTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())
        KConditionWatchers.waitForElement(onView(withText(R.string.historyEventsTitle)), DEFAULT_TIMEOUT_LIMIT).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withBindingAdapter(HistoryEventItemBinding::class)).atPosition(0))
    }

    @Test
    fun capsules_from_menu_should_populate_recycler_by_its() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.capsulesTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())
        KConditionWatchers.waitForElement(onView(withText(R.string.capsulesTitle)), DEFAULT_TIMEOUT_LIMIT).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withBindingAdapter(CapsuleItemBinding::class)).atPosition(0))
    }

    @Test
    fun crew_from_menu_should_populate_recycler_by_them() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.crewTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())
        KConditionWatchers.waitForElement(onView(withText(R.string.crewTitle)), DEFAULT_TIMEOUT_LIMIT).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withBindingAdapter(CrewItemBinding::class)).atPosition(0))
    }

    @Test
    fun cores_from_menu_should_populate_recycler_by_its() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.coresTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())
        KConditionWatchers.waitForElement(onView(withText(R.string.coresTitle)), DEFAULT_TIMEOUT_LIMIT).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withBindingAdapter(CoreItemBinding::class)).atPosition(0))
    }

    @Test
    fun dragons_from_menu_should_populate_recycler_by_its() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.dragonsTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())
        KConditionWatchers.waitForElement(onView(withText(R.string.dragonsTitle)), DEFAULT_TIMEOUT_LIMIT).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withBindingAdapter(DragonItemBinding::class)).atPosition(0))
    }

    @Test
    fun rockets_from_menu_should_populate_recycler_by_its() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.rocketsTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())
        KConditionWatchers.waitForElement(onView(withText(R.string.rocketsTitle)), DEFAULT_TIMEOUT_LIMIT).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withBindingAdapter(RocketItemBinding::class)).atPosition(0))
    }

    @Test
    fun launch_pads_from_menu_should_populate_recycler_by_its() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.launchPadsTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())
        KConditionWatchers.waitForElement(onView(withText(R.string.launchPadsTitle)), DEFAULT_TIMEOUT_LIMIT).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withBindingAdapter(LaunchPadItemBinding::class)).atPosition(0))
    }

    @Test
    fun landing_pads_from_menu_should_populate_recycler_by_its() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.landingPadsTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())
        KConditionWatchers.waitForElement(onView(withText(R.string.landingPadsTitle)), DEFAULT_TIMEOUT_LIMIT).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withBindingAdapter(LandingPadItemBinding::class)).atPosition(0))
    }

    @Test
    fun translate_from_menu_should_create_dialog_and_then_toggle_off_with_toast() {
        KConditionWatchers.waitForElementFullyVisible(onView(withText(R.string.launchesTitle)), DEFAULT_TIMEOUT_LIMIT)
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.aiTranslateTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())
        onView(withText(R.string.aiTranslateOnText)).check(matches(isDisplayed()))
        onView(withText(R.string.agreeText)).perform(click())
        openContextualActionModeOverflowMenu()
        KConditionWatchers.waitForElement(onView(withText(R.string.aiTranslateTitle)), DEFAULT_TIMEOUT_LIMIT).perform(click())

        var decor: View? = null
        activityRule.scenario.onActivity {
            decor = it.window.decorView
        }
        onView(withText(R.string.aiTranslateOffText)).inRoot(withDecorView(not(`is`(decor))))
            .check(matches(isDisplayed()))
    }
}