package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Rocket
import ru.alexmaryin.spacextimes_rx.databinding.RocketItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor
import ru.alexmaryin.spacextimes_rx.utils.expandOrSwapTo
import ru.alexmaryin.spacextimes_rx.utils.swapVisibility

class RocketViewHolder : ViewHolderVisitor {

    override val layout = R.layout.rocket_item

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        with(binding as RocketItemBinding) {
            this.clickListener = clickListener
            rocket = item as Rocket
            expandDescriptionButton.setOnClickListener { it.swapVisibility(); description expandOrSwapTo 5 }
            description.setOnClickListener { expandDescriptionButton.swapVisibility(); description expandOrSwapTo 5 }
        }
    }

    override fun acceptBinding(item: Any): Boolean = item is Rocket
}