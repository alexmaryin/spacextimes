package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.databinding.RocketItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.*
import ru.alexmaryin.spacextimes_rx.utils.expandOrSwapTo
import ru.alexmaryin.spacextimes_rx.utils.swapVisibility

class RocketAdapter(clickListener: AdapterClickListenerById) : BaseListAdapter(clickListener) {

    class ViewHolder(private val binding: RocketItemBinding) : DataViewHolder(binding) {

        override fun bind(item: DataItem, clickListener: AdapterClickListenerById) {
            with(binding) {
                this.clickListener = clickListener
                rocket = item.asData()!!
                expandDescriptionButton.setOnClickListener { it.swapVisibility(); description expandOrSwapTo 5 }
                description.setOnClickListener { expandDescriptionButton.swapVisibility(); description expandOrSwapTo 5  }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder = if (viewType == BODY_TYPE)
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.rocket_item, parent, false)) }
    else super.onCreateViewHolder(parent, viewType)
}