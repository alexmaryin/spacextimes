package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.parts.CrewFlight
import ru.alexmaryin.spacextimes_rx.databinding.FlightCrewItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class FlightCrewViewHolder : ViewHolderVisitor {

    override val layout = R.layout.flight_crew_item

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        with(binding as FlightCrewItemBinding) {
            this.clickListener = clickListener
            flightCrew = item as CrewFlight
        }
    }

    override fun acceptBinding(item: Any): Boolean = item is CrewFlight
}