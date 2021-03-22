package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.databinding.CrewItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder

class CrewAdapter(clickListener: AdapterClickListenerById): BaseListAdapter<Crew>(arrayListOf(), clickListener) {

    class ViewHolder(private val binding: CrewItemBinding) : DataViewHolder<Crew>(binding) {

        override fun bind(item: Crew, clickListener: AdapterClickListenerById) {
            with (binding) {
                this.clickListener = clickListener
                crewMember = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Crew> =
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.crew_item, parent, false)) }
}