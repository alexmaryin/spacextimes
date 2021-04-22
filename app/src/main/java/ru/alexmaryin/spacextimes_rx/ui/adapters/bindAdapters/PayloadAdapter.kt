package ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Payload
import ru.alexmaryin.spacextimes_rx.data.model.enums.PayloadType
import ru.alexmaryin.spacextimes_rx.utils.CircleTransformation

object PayloadAdapter {
    @JvmStatic
    @BindingAdapter("payloadTypeImage")
    fun setImage(view: View, payload: Payload) {
        Picasso.get()
            .load(when(payload.type) {
            PayloadType.SATELLITE -> R.drawable.satellite
            PayloadType.DRAGON_BOILERPLATE -> R.drawable.dragon_boilerplate
            PayloadType.DRAGON_1_0 -> R.drawable.dragon_1_0_foreground
            PayloadType.DRAGON_1_1 -> R.drawable.dragon_1_1_foreground
            PayloadType.DRAGON_2_0 -> R.drawable.dragon_2_0_foreground
            PayloadType.CREW_DRAGON -> R.drawable.dragon_crew
            PayloadType.LANDER -> R.drawable.payload_lander
        })
            .transform(CircleTransformation())
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(view as ImageView)
    }

    @JvmStatic
    @BindingAdapter("payloadType")
    fun setPayloadType(view: View, payload: Payload) {
        (view as TextView).text = payload.typeAsString(view.context)
    }

    @JvmStatic
    @BindingAdapter("payloadShortString")
    fun setPayloadShort(view: View, payload: Payload) {
        val customersStr = if (payload.customers.isNotEmpty())
            view.resources.getQuantityString(R.plurals.payload_customer_string, payload.customers.size, payload.customers.joinToString())
        else null
        val manufacturersStr = if (payload.manufacturers.isNotEmpty())
                view.resources.getQuantityString(R.plurals.payload_manufacturer_string, payload.manufacturers.size, payload.manufacturers.joinToString())
        else null
        (view as TextView).text = listOfNotNull(customersStr, manufacturersStr).joinToString()
    }
}