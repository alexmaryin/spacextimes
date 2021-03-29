package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.databinding.CrewItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.*

class CrewAdapter(clickListener: AdapterClickListenerById): BaseListAdapter(clickListener) {

    class ViewHolder(private val binding: CrewItemBinding) : DataViewHolder(binding) {

        override fun bind(item: DataItem, clickListener: AdapterClickListenerById) {
            with (binding) {
                this.clickListener = clickListener
                crewMember = item.asData()!!
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder = if (viewType == BODY_TYPE)
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.crew_item, parent, false)) }
    else super.onCreateViewHolder(parent, viewType)
}