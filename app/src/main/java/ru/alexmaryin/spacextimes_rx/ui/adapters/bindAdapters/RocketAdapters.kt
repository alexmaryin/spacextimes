package ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Rocket

object RocketAdapters {
    @JvmStatic
    @BindingAdapter("rocketShortStatus")
    fun postStatusLine(view: TextView, rocket: Rocket?) {
        rocket?.let {
            with(view) {
                setCompoundDrawablesRelativeWithIntrinsicBounds(if (rocket.active) R.drawable.ic_active else R.drawable.ic_destroyed, 0, 0, 0)
                text = resources.getString(if (rocket.active) R.string.activeText else R.string.destroyedText)
            }
        }
    }
}