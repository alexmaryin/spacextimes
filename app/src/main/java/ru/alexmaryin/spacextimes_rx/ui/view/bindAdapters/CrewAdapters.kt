package ru.alexmaryin.spacextimes_rx.ui.view.bindAdapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.enums.CrewStatus

object CrewAdapters {
    @JvmStatic
    @BindingAdapter("crewStatus")
    fun postStatus(view: TextView, status: CrewStatus?) {
        status?.let {
            with(view) {
                when(it) {
                    CrewStatus.UNKNOWN -> {
                        text = context.getString(R.string.unknownText)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_unknown, 0, 0, 0)
                    }
                    CrewStatus.ACTIVE -> {
                        text = context.getString(R.string.activeText)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_active, 0, 0, 0)
                    }
                    CrewStatus.INACTIVE -> {
                        text = context.getString(R.string.inactiveText)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_destroyed, 0, 0, 0)
                    }
                    CrewStatus.RETIRED -> {
                        text = context.getString(R.string.retiredText)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_retired, 0, 0, 0)
                    }
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("hideIfEmpty")
    fun setVisibility(view: View, agency: String?) {
        view.visibility = if(agency.isNullOrBlank()) View.GONE else View.VISIBLE
    }
}