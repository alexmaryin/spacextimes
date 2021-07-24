package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.parts.CoreFlight
import ru.alexmaryin.spacextimes_rx.databinding.FlightCoreItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor
import ru.alexmaryin.spacextimes_rx.ui.adapters.emptyClickListener
import ru.alexmaryin.spacextimes_rx.utils.falconResourceName

class FlightCoreViewHolder : ViewHolderVisitor {

    override val layout = R.layout.flight_core_item

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        with(binding as FlightCoreItemBinding) {
            flightCore = item as CoreFlight
            this.clickListener = if (item.core != null && item.core!!.totalFlights > 0) clickListener else emptyClickListener

            item.core?.let {
                coreThumbnail.setImageResource(root.resources.getIdentifier(falconResourceName(it), "drawable", root.context.packageName))
            }
        }
    }

    override fun acceptBinding(item: Any): Boolean = item is CoreFlight
}