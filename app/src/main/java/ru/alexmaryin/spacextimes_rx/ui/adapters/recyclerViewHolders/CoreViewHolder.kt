package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Cores
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.CoreStatus
import ru.alexmaryin.spacextimes_rx.databinding.CoreItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor

class CoreViewHolder : ViewHolderVisitor {

    override val layout = R.layout.core_item

    private fun falconResourceName(item: Cores) = if (item.block == null) "falcon1" else buildString {
        append("falcon9block${item.block}")
        append(if (item.block > 4 || item.totalFlights() >= 2) "legs_" else "_")
        append(
            when {
                item.totalFlights() < 1 -> 1
                item.totalFlights() > 10 -> 10
                else -> item.totalFlights()
            }
        )
    }

    override fun bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById) {
        val core = item as Cores
        with(binding as CoreItemBinding) {
            this.core = core
            this.clickListener = if (core.totalFlights() > 0) clickListener else AdapterClickListenerById {
                Toast.makeText(root.context, root.context.getString(R.string.core_not_fly_text), Toast.LENGTH_LONG).show()
            }

            coreThumbnail.setImageResource(root.resources.getIdentifier(falconResourceName(core), "drawable", root.context.packageName))
            when (core.status) {
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
                append(
                    if (core.totalFlights() > 0)
                        root.resources.getQuantityString(R.plurals.reuseCountString, core.totalFlights(), core.totalFlights())
                    else root.resources.getString(R.string.noFlightString)
                )
                if (core.groundLandAttempts > 0) append(root.context.getString(R.string.groundLandCoreCountString, core.groundLandings, core.groundLandAttempts))
                if (core.waterLandAttempts > 0) append(root.context.getString(R.string.waterLandCoreCountString, core.waterLandings, core.waterLandAttempts))
            }
        }
    }

    override fun acceptVisitor(item: HasStringId): Boolean = item is Cores
}