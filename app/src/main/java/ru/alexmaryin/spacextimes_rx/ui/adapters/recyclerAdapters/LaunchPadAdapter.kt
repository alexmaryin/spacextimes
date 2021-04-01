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
import ru.alexmaryin.spacextimes_rx.ui.adapters.*

class LaunchPadAdapter(clickListener: AdapterClickListenerById) : BaseListAdapter(clickListener) {

    class ViewHolder(private val binding: LaunchPadItemBinding) : DataViewHolder(binding) {

        override fun bind(item: DataItem, clickListener: AdapterClickListenerById) {
            val launchPad = item.asData<LaunchPad>()!!
            with(binding) {
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder = if (viewType == BODY_TYPE)
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.launch_pad_item, parent, false)) }
    else super.onCreateViewHolder(parent, viewType)
}