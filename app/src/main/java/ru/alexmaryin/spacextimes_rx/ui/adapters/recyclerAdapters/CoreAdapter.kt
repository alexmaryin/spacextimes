package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.CoreStatus
import ru.alexmaryin.spacextimes_rx.databinding.CoreItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder

class CoreAdapter(clickListener: AdapterClickListenerById): BaseAdapter<Core>(arrayListOf(), clickListener) {

    class ViewHolder (private val binding: CoreItemBinding): DataViewHolder<Core>(binding) {

        override fun bind(item: Core, clickListener: AdapterClickListenerById) {
            with (binding) {
                core = item
                coreThumbnail.setImageResource(
                    when (item.block) {
                        null -> R.drawable.falcon1_block0
                        1 -> R.drawable.falcon9_block1
                        2,3 -> R.drawable.falcon9_block2_3
                        4 -> R.drawable.falcon9_block4
                        5 -> R.drawable.falcon9_block5
                        else -> R.drawable.falcon_heavy
                    })

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
                    if (item.reuseCount > 0) append(
                        root.resources.getString(R.string.reuseText) +
                        root.resources.getQuantityString(R.plurals.reuseCountString, item.reuseCount, item.reuseCount)
                    )
                    if (item.groundLandAttempts > 0) append(root.context.getString(R.string.groundLandCoreCountString, item.groundLandings, item.groundLandAttempts))
                    if (item.waterLandAttempts > 0) append(root.context.getString(R.string.waterLandCoreCountString, item.waterLandings, item.waterLandAttempts))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Core> =
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.core_item, parent, false)) }

}