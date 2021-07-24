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
import ru.alexmaryin.spacextimes_rx.data.model.extra.Images
import ru.alexmaryin.spacextimes_rx.data.model.parts.CoreFlight
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
    @BindingAdapter(value = ["imagesList", "roundedCorners"], requireAll = false)
    fun loadImageFromList(view: ImageView, images: Images, roundedCorners: Boolean = false) {
        val source = images.large.ifEmpty { images.small.ifEmpty { null } }
        source?.let {
            loadImage(view, it.random(), roundedCorners)
        }
    }

    @JvmStatic
    @BindingAdapter("url")
    fun openLink(view: View, url: String?) {
        url?.let {
            view.setOnClickListener {
                val intent = if (url.endsWith("pdf", true)) {
                    Intent(Intent.ACTION_VIEW, Uri.parse(url)).run {
                        type = "application/pdf"
                        Intent.createChooser(this, "Открыть pdf").apply { flags += Intent.FLAG_ACTIVITY_NEW_TASK }
                    }
                } else Intent(Intent.ACTION_VIEW, Uri.parse(url))
                it.context.startActivity(intent)
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
        view.visibility = if (value != null) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["carouselSource", "launchName"], requireAll = true)
    fun populateCarousel(view: View, images: List<String>?, launchName: String = "") {
        images?.filter { it.isNotBlank() }?.let { imagesList ->
            with(view as CarouselView) {
                visibility = View.VISIBLE
                setImageListener { position, imageView ->
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    loadImage(imageView, imagesList[position])
                }
                setImageClickListener(downloadImageFromCarousel(context, imagesList, "images_$launchName.jpg"))
                pageCount = imagesList.size
            }
        }
    }

    @JvmStatic
    @BindingAdapter("coreLandingText")
    fun buildCoreLandingText(view: TextView, core: CoreFlight) = buildString {
        append(view.context.getString(R.string.landing_attempts_caption))
        core.landingSuccess?.let { landed ->
            append(view.context.getString(if (landed) R.string.landing_success_string else R.string.landing_fail_string, core.landingType))
        } ?: append(view.context.getString(R.string.no_landing_attempt_string))
    }
}