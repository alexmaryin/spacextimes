package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.LaunchPad
import ru.alexmaryin.spacextimes_rx.databinding.LaunchPadItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder

class LaunchPadAdapter(clickListener: AdapterClickListenerById) : BaseListAdapter<LaunchPad>(clickListener) {

    class ViewHolder(private val binding: LaunchPadItemBinding) : DataViewHolder<LaunchPad>(binding) {

        override fun bind(item: LaunchPad, clickListener: AdapterClickListenerById) {
            with(binding) {
                this.clickListener = clickListener
                launchPad = item

                launchPadLocation.setOnClickListener {
                    val gmmIntentUri = Uri.parse("geo:${item.latitude},${item.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    mapIntent.resolveActivity(root.context.packageManager)?.let {
                        startActivity(root.context, mapIntent, null)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<LaunchPad> =
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.launch_pad_item, parent, false)) }
}