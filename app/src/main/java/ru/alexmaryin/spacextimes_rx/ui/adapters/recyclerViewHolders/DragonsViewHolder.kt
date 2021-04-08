package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import ru.alexmaryin.spacextimes_rx.databinding.DragonItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor
import ru.alexmaryin.spacextimes_rx.utils.expandOrSwapTo
import ru.alexmaryin.spacextimes_rx.utils.swapVisibility

class DragonsViewHolder : ViewHolderVisitor {

    override val layout = R.layout.dragon_item

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        with(binding as DragonItemBinding) {
            this.clickListener = clickListener
            dragon = item as Dragon
            expandDescriptionButton.setOnClickListener { it.swapVisibility(); description expandOrSwapTo 5 }
            description.setOnClickListener { expandDescriptionButton.swapVisibility(); description expandOrSwapTo 5 }
        }
    }

    override fun acceptBinding(item: Any): Boolean = item is Dragon
}