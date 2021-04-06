package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.LaunchPad
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.LaunchPadItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class LaunchPadViewHolder : ViewHolderVisitor {

    override val layout = R.layout.launch_pad_item

    override fun bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById) {
        val launchPad = item as LaunchPad
        with(binding as LaunchPadItemBinding) {
            this.clickListener = clickListener
            this.launchPad = launchPad

            launchPadLocation.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${launchPad.latitude},${launchPad.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                mapIntent.resolveActivity(root.context.packageManager)?.let {
                    startActivity(root.context, mapIntent, null)
                }
            }
        }
    }

    override fun acceptBinding(item: HasStringId): Boolean = item is LaunchPad
}