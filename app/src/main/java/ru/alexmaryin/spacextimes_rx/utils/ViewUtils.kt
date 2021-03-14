package ru.alexmaryin.spacextimes_rx.utils

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView

fun View.swapVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

infix fun TextView.expandOrSwapTo(lines: Int) {
    maxLines = if (maxLines == lines) Integer.MAX_VALUE else lines
}

@Suppress("DEPRECATION")
fun Context.getCurrentLocale(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
    resources.configuration.locales[0].language else resources.configuration.locale.language