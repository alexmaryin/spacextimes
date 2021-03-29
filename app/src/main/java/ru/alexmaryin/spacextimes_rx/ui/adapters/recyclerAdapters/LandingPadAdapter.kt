package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.LandingPad
import ru.alexmaryin.spacextimes_rx.databinding.LandingPadItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.*

class LandingPadAdapter(clickListener: AdapterClickListenerById) : BaseListAdapter(clickListener) {

    class ViewHolder(private val binding: LandingPadItemBinding) : DataViewHolder(binding) {

        override fun bind(item: DataItem, clickListener: AdapterClickListenerById) {
            val landingPad = item.asData<LandingPad>()!!
            with(binding) {
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder = if (viewType == BODY_TYPE)
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.landing_pad_item, parent, false)) }
    else super.onCreateViewHolder(parent, viewType)
}