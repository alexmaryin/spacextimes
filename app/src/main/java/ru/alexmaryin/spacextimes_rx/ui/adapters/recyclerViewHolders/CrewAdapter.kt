package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.data.model.Crews
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.CrewItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterVisitor

class CrewViewHolder : AdapterVisitor {

        override fun bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById) {
            with (binding as CrewItemBinding) {
                this.clickListener = clickListener
                crewMember = item as Crews
            }
        }

    override fun acceptVisitor(item: HasStringId): Boolean = item is Crews
}