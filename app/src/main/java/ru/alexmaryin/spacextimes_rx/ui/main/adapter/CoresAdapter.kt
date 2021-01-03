package ru.alexmaryin.spacextimes_rx.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.ui.base.BaseAdapter
import ru.alexmaryin.spacextimes_rx.ui.base.DataViewHolder

class CoresAdapter: BaseAdapter<Core>(items = ArrayList()) {

    class ViewHolder(itemView: View): DataViewHolder<Core>(itemView) {
        override fun bind (item: Core) {
            itemView.findViewById<TextView>(R.id.nameText).apply { text = item.serial }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Core> =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.crew_item, parent, false))

}