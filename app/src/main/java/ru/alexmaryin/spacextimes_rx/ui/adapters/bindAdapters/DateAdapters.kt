package ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launches
import java.text.DateFormat
import java.util.*

object DateAdapter {

    @JvmStatic
    @BindingAdapter("dateLong")
    fun dateToString(view: TextView, date: Date?) {
        date?.let {
            view.text = DateFormat.getDateInstance(DateFormat.LONG).format(date)
        }
    }

    @JvmStatic
    @BindingAdapter("dateBind")
    fun firstFlightToString(view: TextView, date: Date?) {
        date?.let {
            view.text = view.context.getString(R.string.first_flight_text, DateFormat.getDateInstance(DateFormat.LONG).format(date))
        }
    }

    @JvmStatic
    @BindingAdapter("launchDateFormat")
    fun launchDateToString(view: TextView, launch: Launches?) {
        launch?.let {
            if (it.toBeDetermined) {
                view.text = view.context.getString(R.string.to_be_determined_string)
            } else {
                view.text = (if (it.notEarlyThan) view.context.getString(R.string.not_early_string) else "") + launch.dateTrimmed(view.context)
            }
        }
    }
}