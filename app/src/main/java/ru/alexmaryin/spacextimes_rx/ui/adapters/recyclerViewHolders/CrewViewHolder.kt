package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.databinding.CrewItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class CrewViewHolder : ViewHolderVisitor {

    override val layout = R.layout.crew_item

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        with(binding as CrewItemBinding) {
            this.clickListener = clickListener
            crewMember = item as Crew
        }
    }

    override fun acceptBinding(item: Any): Boolean = item is Crew
}