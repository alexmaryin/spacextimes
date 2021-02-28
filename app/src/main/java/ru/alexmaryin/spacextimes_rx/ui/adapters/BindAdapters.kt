package ru.alexmaryin.spacextimes_rx.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import ru.alexmaryin.spacextimes_rx.R

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