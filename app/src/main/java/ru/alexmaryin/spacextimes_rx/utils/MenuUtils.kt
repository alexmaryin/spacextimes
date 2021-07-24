package ru.alexmaryin.spacextimes_rx.utils

import android.view.MenuItem
import androidx.appcompat.widget.SearchView

class MenuItemCollapseListener(private val collapseCallback: (item: MenuItem?) -> Unit) :
    MenuItem.OnActionExpandListener {
    override fun onMenuItemActionExpand(item: MenuItem?) = true

    override fun onMenuItemActionCollapse(item: MenuItem?) = run {
        collapseCallback(item)
        true
    }
}

class HotSearchListener(
    private val closeKeyboardCallback: () -> Unit,
    private val textChangeCallback: (text: String) -> Unit
) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = run {
        closeKeyboardCallback()
        onQueryTextChange(query)
    }

    override fun onQueryTextChange(newText: String?) = newText?.run {
        textChangeCallback(this)
        true
    } ?: false

}