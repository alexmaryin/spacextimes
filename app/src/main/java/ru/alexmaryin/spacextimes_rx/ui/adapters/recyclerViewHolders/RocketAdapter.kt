package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.data.model.Rocket
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.RocketItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterVisitor
import ru.alexmaryin.spacextimes_rx.utils.expandOrSwapTo
import ru.alexmaryin.spacextimes_rx.utils.swapVisibility

class RocketViewHolder : AdapterVisitor {

        override fun bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById) {
            with(binding as RocketItemBinding) {
                this.clickListener = clickListener
                rocket = item as Rocket
                expandDescriptionButton.setOnClickListener { it.swapVisibility(); description expandOrSwapTo 5 }
                description.setOnClickListener { expandDescriptionButton.swapVisibility(); description expandOrSwapTo 5  }
            }
        }

    override fun acceptVisitor(item: HasStringId): Boolean = item is Rocket
}