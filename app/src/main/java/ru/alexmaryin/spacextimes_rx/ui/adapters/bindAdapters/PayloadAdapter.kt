package ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Payload
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleType
import ru.alexmaryin.spacextimes_rx.utils.CircleTransformation

object PayloadAdapter {
    @JvmStatic
    @BindingAdapter("payloadTypeImage")
    fun setImage(view: View, payload: Payload) {
        if(payload.dragon.isNotEmpty()) {
            view.visibility = View.VISIBLE
            Picasso.get()
                .load(
                    when (payload.dragon.capsule?.type) {
                        CapsuleType.DRAGON1_0 -> R.drawable.dragon_1_0_foreground
                        CapsuleType.DRAGON1_1 -> R.drawable.dragon_1_1_foreground
                        CapsuleType.DRAGON2_0 -> R.drawable.dragon_2_0_foreground
                        else -> R.drawable.ic_broken_image
                    }
                )
                .transform(CircleTransformation())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(view as ImageView)
        } else view.visibility = View.GONE
    }
}