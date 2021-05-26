package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import android.view.View
import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.History
import ru.alexmaryin.spacextimes_rx.databinding.HistoryEventItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class HistoryEventsViewHolder : ViewHolderVisitor {

    override val layout = R.layout.history_event_item

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        val historyEvent = item as History
        with(binding as HistoryEventItemBinding) {
            this.historyEvent = historyEvent
            eventLinkText.visibility = historyEvent.links?.article?.run { View.VISIBLE } ?: View.GONE
        }
    }

    override fun acceptBinding(item: Any): Boolean = item is History
}