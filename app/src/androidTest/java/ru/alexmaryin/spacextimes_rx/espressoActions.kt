package ru.alexmaryin.spacextimes_rx

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import kotlin.reflect.KClass

fun clickItemWithId(id: Int)  = object : ViewAction {
    override fun getConstraints(): Matcher<View>? = null

    override fun getDescription() = "Click on a child view specified id."

    override fun perform(uiController: UiController?, view: View) {
        (view.findViewById(id) as View).performClick()
    }
}

fun longClickItemWithId(id: Int)  = object : ViewAction {
    override fun getConstraints(): Matcher<View>? = null

    override fun getDescription() = "Long click on a child view specified id."

    override fun perform(uiController: UiController?, view: View) {
        (view.findViewById(id) as View).performLongClick()
    }
}

fun <T: ViewDataBinding> withBindingAdapter(cls: KClass<T>) = object : BoundedMatcher<RecyclerView.ViewHolder, BaseListAdapter.DataViewHolder>(BaseListAdapter.DataViewHolder::class.java) {
    override fun describeTo(description: Description?) {
        description?.appendText("No view holder found with ${cls.simpleName}")
    }

    override fun matchesSafely(item: BaseListAdapter.DataViewHolder): Boolean = cls.isInstance(item.binding)
}