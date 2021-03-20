package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleType
import ru.alexmaryin.spacextimes_rx.databinding.CapsuleItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder

class CapsuleAdapter(clickListener: AdapterClickListenerById): BaseAdapter<Capsule>(arrayListOf(), clickListener) {

    class ViewHolder (private val binding: CapsuleItemBinding): DataViewHolder<Capsule>(binding) {

        override fun bind(item: Capsule, clickListener: AdapterClickListenerById) {
            with (binding) {
                capsule = item
                capsuleImage.setImageResource(
                    when (item.type) {
                        CapsuleType.DRAGON1_0 -> R.drawable.dragon1_0
                        CapsuleType.DRAGON1_1 -> R.drawable.dragon1_1
                        CapsuleType.DRAGON2_0 -> R.drawable.dragon2_0
                    })

                when (item.status) {
                    CapsuleStatus.UNKNOWN -> {
                        capsuleStatus.text = root.context.getString(R.string.unknownText)
                        capsuleStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_unknown, 0, 0, 0)
                        capsuleImage.alpha = 0.5F
                    }
                    CapsuleStatus.ACTIVE -> {
                        capsuleStatus.text = root.context.getString(R.string.activeText)
                        capsuleStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_active, 0, 0, 0)
                        capsuleImage.alpha = 1F
                    }
                    CapsuleStatus.RETIRED -> {
                        capsuleStatus.text = root.context.getString(R.string.retiredText)
                        capsuleStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_retired, 0, 0, 0)
                        capsuleImage.alpha = 0.8F
                    }
                    CapsuleStatus.DESTROYED -> {
                        capsuleStatus.text = root.context.getString(R.string.destroyedText)
                        capsuleStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_destroyed, 0, 0, 0)
                        capsuleImage.alpha = 0.4F
                    }
                }

                capsuleReused.text = buildString {
                    if (item.reuseCount > 0) append(root.resources.getQuantityString(R.plurals.reuseCountString, item.reuseCount, item.reuseCount))
                    if (item.landLandings > 0) append(root.context.getString(R.string.groundLandCountString, item.landLandings))
                    if (item.waterLandings > 0) append(root.context.getString(R.string.waterLandCountString, item.waterLandings))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Capsule> =
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.capsule_item, parent, false)) }

}



