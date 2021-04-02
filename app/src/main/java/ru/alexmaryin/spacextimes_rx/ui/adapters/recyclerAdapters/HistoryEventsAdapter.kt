package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.History
import ru.alexmaryin.spacextimes_rx.databinding.HistoryEventItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.*

class HistoryEventsAdapter(clickListener: AdapterClickListenerById) : BaseListAdapter(clickListener) {

    class ViewHolder(private val binding: HistoryEventItemBinding) : DataViewHolder(binding) {

        override fun bind(item: DataItem, clickListener: AdapterClickListenerById) {
            val historyEvent = item.asData<History>()!!
            with(binding) {
//                this.clickListener = clickListener
                this.historyEvent = historyEvent
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder = if (viewType == BODY_TYPE)
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.history_event_item, parent, false)) }
    else super.onCreateViewHolder(parent, viewType)
}