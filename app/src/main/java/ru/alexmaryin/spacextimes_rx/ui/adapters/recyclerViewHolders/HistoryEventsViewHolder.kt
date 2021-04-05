package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.History
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.HistoryEventItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class HistoryEventsViewHolder : ViewHolderVisitor {

    override val layout = R.layout.history_event_item

    override fun bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById) {
        val historyEvent = item as History
        with(binding as HistoryEventItemBinding) {
            this.historyEvent = historyEvent
        }
    }

    override fun acceptVisitor(item: HasStringId): Boolean = item is History
}