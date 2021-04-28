package ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.synnapps.carouselview.CarouselView
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.utils.CircleTransformation
import ru.alexmaryin.spacextimes_rx.utils.downloadImageFromCarousel

object CommonAdapters {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "roundedCorners"], requireAll = false)
    fun loadImage(view: ImageView, url: String?, roundedCorners: Boolean = false) {
        if (url != null) {

            val noTransform = object : Transformation {
                override fun transform(source: Bitmap?): Bitmap = source!!
                override fun key(): String = "noTransform()"
            }

            val transformation: Transformation = if (roundedCorners) CircleTransformation() else noTransform

            view.visibility = View.VISIBLE
            Picasso.get()
                .load(url)
                .resize(Resources.getSystem().displayMetrics.widthPixels, 0)
                .transform(transformation)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(view)
        } else view.visibility = View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("url")
    fun openLink(view: View, url: String?) {
        url?.let {
            view.setOnClickListener {
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(url)
                }.run { it.context.startActivity(this) }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("underlined")
    fun underline(view: TextView, underlined: Boolean) {
        if (underlined) view.paintFlags = view.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    @JvmStatic
    @BindingAdapter("checkVisibility")
    fun setVisibility(view: View, value: Any?) {
        view.visibility = if(value != null) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["carouselSource", "launchName"], requireAll = true)
    fun populateCarousel(view: View, images: List<String>?, launchName: String = "") {
        images?.let {
            with(view as CarouselView) {
                visibility = View.VISIBLE
                setImageListener { position, imageView ->
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    loadImage(imageView, images[position])
                }
                setImageClickListener(downloadImageFromCarousel(context, images, "images_$launchName.jpg"))
                pageCount = images.size
            }
        }
    }
}