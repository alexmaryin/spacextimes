package ru.alexmaryin.spacextimes_rx.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleType
import ru.alexmaryin.spacextimes_rx.ui.base.BaseAdapter
import ru.alexmaryin.spacextimes_rx.ui.base.DataViewHolder

class CapsuleAdapter: BaseAdapter<Capsule>(arrayListOf()) {

    class ViewHolder(itemView: View) : DataViewHolder<Capsule>(itemView) {
        override fun bind(item: Capsule) {
            itemView.findViewById<TextView>(R.id.capsuleSerial).apply { text = item.serial }

            val capsuleImage = itemView.findViewById<ImageView>(R.id.capluseImage)
            capsuleImage.setImageResource(
                when (item.type) {
                    CapsuleType.DRAGON1_0 -> R.drawable.dragon1_0
                    CapsuleType.DRAGON1_1 -> R.drawable.dragon1_1
                    CapsuleType.DRAGON2_0 -> R.drawable.dragon2_0
                }
            )

            itemView.findViewById<TextView>(R.id.capsuleStatus).apply {
                when (item.status) {
                    CapsuleStatus.UNKNOWN -> {
                        text = context.getString(R.string.unknownText)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_unknown, 0, 0, 0)
                        capsuleImage.alpha = 0.5F
                    }
                    CapsuleStatus.ACTIVE -> {
                        text = context.getString(R.string.activeText)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_active, 0, 0, 0)
                        capsuleImage.alpha = 1F
                    }
                    CapsuleStatus.RETIRED -> {
                        text = context.getString(R.string.retiredText)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_retired, 0, 0, 0)
                        capsuleImage.alpha = 0.8F
                    }
                    CapsuleStatus.DESTROYED -> {
                        text = context.getString(R.string.destroyedText)
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_destroyed, 0, 0, 0)
                        capsuleImage.alpha = 0.4F
                    }
                }
            }

            itemView.findViewById<TextView>(R.id.capsuleReused).apply {
                text = buildString {
                    if (item.reuseCount > 0) append(resources.getQuantityString(R.plurals.reuseCountString, item.reuseCount, item.reuseCount))
                    if (item.landLandings > 0) append(context.getString(R.string.groundLandCountString, item.landLandings))
                    if (item.waterLandings > 0) append(context.getString(R.string.waterLandCountString, item.waterLandings))
                }
            }

            itemView.findViewById<TextView>(R.id.capsuleUpdate).apply { text = item.lastUpdate }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Capsule> =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.capsule_item, parent, false))
}


