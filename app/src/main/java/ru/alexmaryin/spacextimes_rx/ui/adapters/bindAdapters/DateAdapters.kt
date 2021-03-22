package ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.alexmaryin.spacextimes_rx.R
import java.text.DateFormat
import java.util.*

object DateAdapter {
    @JvmStatic
    @BindingAdapter("dateBind")
    fun firstFlightToString(view: TextView, date: Date?) {
        date?.let {
            view.text = view.context.getString(R.string.first_flight_text, DateFormat.getDateInstance(DateFormat.LONG).format(date))
        }
    }

    @JvmStatic
    @BindingAdapter("longDateStr")
    fun dateToLongString(view: TextView, date: Date?) {
        date?.let {
            view.text = DateFormat.getDateInstance(DateFormat.LONG).format(date)
        }
    }
}