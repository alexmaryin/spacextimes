package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.databinding.LaunchItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder

class LaunchesAdapter(clickListener: AdapterClickListenerById) : BaseListAdapter<Launch>(arrayListOf(), clickListener) {

    class ViewHolder(private val binding: LaunchItemBinding) : DataViewHolder<Launch>(binding) {

        override fun bind(item: Launch, clickListener: AdapterClickListenerById) {
            with(binding) {
                this.clickListener = clickListener
                launch = item
                box.setBackgroundColor(root.resources.getColor( when {
                    item.upcoming -> R.color.design_default_color_background
                    item.success -> R.color.success_color
                    else -> R.color.fail_color
                }, null))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Launch> =
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.launch_item, parent, false)) }
}