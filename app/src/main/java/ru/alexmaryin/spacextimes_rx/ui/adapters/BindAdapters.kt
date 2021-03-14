package ru.alexmaryin.spacextimes_rx.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.CrewStatus
import java.text.DateFormat
import java.util.*

object ImageAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        url?.let {
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_broken_image)
                .into(view)
        }
    }
}

object DateAdapter {
    @JvmStatic
    @BindingAdapter("dateBind")
    fun dateToString(view: TextView, date: Date?) {
        date?.let {
            view.text = DateFormat.getDateInstance(DateFormat.LONG).format(date)
        }
    }
}

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