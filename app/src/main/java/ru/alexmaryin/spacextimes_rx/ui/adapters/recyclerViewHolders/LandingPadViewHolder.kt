package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.lists.LandingPads
import ru.alexmaryin.spacextimes_rx.databinding.LandingPadItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class LandingPadViewHolder : ViewHolderVisitor {

    override val layout = R.layout.landing_pad_item

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        val landingPad = item as LandingPads
        with(binding as LandingPadItemBinding) {
            this.clickListener = clickListener
            this.landingPad = landingPad

            landingPadLocation.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${landingPad.latitude},${landingPad.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                mapIntent.resolveActivity(root.context.packageManager)?.let {
                    ContextCompat.startActivity(root.context, mapIntent, null)
                }
            }
        }
    }

    override fun acceptBinding(item: Any): Boolean = item is LandingPads
}