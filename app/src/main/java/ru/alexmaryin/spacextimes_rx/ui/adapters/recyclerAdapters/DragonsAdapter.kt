package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import ru.alexmaryin.spacextimes_rx.databinding.DragonItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder

class DragonsAdapter (clickListener: AdapterClickListenerById): BaseAdapter<Dragon>(arrayListOf(), clickListener) {

    class ViewHolder(private val binding: DragonItemBinding) : DataViewHolder<Dragon>(binding) {

        override fun bind(item: Dragon, clickListener: AdapterClickListenerById) {
            with (binding) {
                dragon = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Dragon> =
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.dragon_item, parent, false)) }
}