package ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Dragon

object DragonAdapters {
    @JvmStatic
    @BindingAdapter("dragonShortStatus")
    fun postStatusLine(view: TextView, dragon: Dragon?) {
        dragon?.let {
            with(view) {
                setCompoundDrawablesRelativeWithIntrinsicBounds(if (dragon.isActive) R.drawable.ic_active else R.drawable.ic_destroyed, 0, 0, 0)
                text = resources.getString(if (dragon.isActive) R.string.activeText else R.string.destroyedText)
            }
        }
    }
}