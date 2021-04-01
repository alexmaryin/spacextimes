package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Capsules
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleType
import ru.alexmaryin.spacextimes_rx.databinding.CapsuleItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.*
import ru.alexmaryin.spacextimes_rx.utils.CircleTransformation

class CapsuleAdapter(clickListener: AdapterClickListenerById): BaseListAdapter(clickListener) {

    class ViewHolder (private val binding: CapsuleItemBinding): DataViewHolder(binding) {

        override fun bind(item: DataItem, clickListener: AdapterClickListenerById) {
            val capsule = item.asData<Capsules>()!!
            with (binding) {
                this.capsule = capsule

                Picasso.get()
                    .load(when (capsule.type) {
                        CapsuleType.DRAGON1_0 -> R.drawable.dragon_1_0_foreground
                        CapsuleType.DRAGON1_1 -> R.drawable.dragon_1_1_foreground
                        CapsuleType.DRAGON2_0 -> R.drawable.dragon_2_0_foreground
                    })
                    .transform(CircleTransformation())
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.ic_broken_image)
                    .into(capsuleImage)

                when (capsule.status) {
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
                    if (capsule.reuseCount > 0) append(root.resources.getQuantityString(R.plurals.reuseCountString, capsule.reuseCount, capsule.reuseCount))
                    if (capsule.landLandings > 0) append(root.context.getString(R.string.groundLandCountString, capsule.landLandings))
                    if (capsule.waterLandings > 0) append(root.context.getString(R.string.waterLandCountString, capsule.waterLandings))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType == BODY_TYPE)
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.capsule_item, parent, false)) }
    else super.onCreateViewHolder(parent, viewType)
}



