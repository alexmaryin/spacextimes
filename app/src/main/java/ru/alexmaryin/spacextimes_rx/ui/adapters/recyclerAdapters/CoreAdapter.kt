package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.enums.CoreStatus
import ru.alexmaryin.spacextimes_rx.databinding.CoreItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder

class CoreAdapter(clickListener: AdapterClickListenerById): BaseListAdapter<Core>(clickListener) {

    class ViewHolder (private val binding: CoreItemBinding): DataViewHolder<Core>(binding) {

        private fun falconResourceName(item: Core) = if (item.block == null) "falcon1" else buildString {
            append("falcon9block${item.block}")
            append(if (item.block > 4 || item.totalFlights() >= 2) "legs_" else "_")
            append(when {
                item.totalFlights() < 1 -> 1
                item.totalFlights() > 10 -> 10
                else -> item.totalFlights()
            })
        }

        override fun bind(item: Core, clickListener: AdapterClickListenerById) {
            with (binding) {
                core = item
                coreThumbnail.setImageResource(root.resources.getIdentifier(falconResourceName(item), "drawable", root.context.packageName))
                when (item.status) {
                    CoreStatus.UNKNOWN -> {
                        coreStatus.text = root.context.getString(R.string.unknownText)
                        coreStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_unknown, 0, 0, 0)
                    }
                    CoreStatus.ACTIVE -> {
                        coreStatus.text = root.context.getString(R.string.activeText)
                        coreStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_active, 0, 0, 0)
                    }
                    CoreStatus.INACTIVE -> {
                        coreStatus.text = root.context.getString(R.string.inactiveText)
                        coreStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_unknown, 0, 0, 0)
                    }
                    CoreStatus.RETIRED -> {
                        coreStatus.text = root.context.getString(R.string.retiredText)
                        coreStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_retired, 0, 0, 0)
                    }
                    CoreStatus.LOST, CoreStatus.EXPENDED -> {
                        coreStatus.text = root.context.getString(R.string.destroyedText)
                        coreStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_destroyed, 0, 0, 0)
                    }
                }

                coreReusing.text = buildString {
                    append(if (item.totalFlights() > 0)
                            root.resources.getQuantityString(R.plurals.reuseCountString, item.totalFlights(), item.totalFlights())
                        else root.resources.getString(R.string.noFlightString))
                    if (item.groundLandAttempts > 0) append(root.context.getString(R.string.groundLandCoreCountString, item.groundLandings, item.groundLandAttempts))
                    if (item.waterLandAttempts > 0) append(root.context.getString(R.string.waterLandCoreCountString, item.waterLandings, item.waterLandAttempts))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Core> =
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.core_item, parent, false)) }

}