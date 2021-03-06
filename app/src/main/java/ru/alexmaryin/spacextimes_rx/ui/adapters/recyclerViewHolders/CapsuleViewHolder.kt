package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.squareup.picasso.Picasso
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleType
import ru.alexmaryin.spacextimes_rx.databinding.CapsuleItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor
import ru.alexmaryin.spacextimes_rx.utils.CircleTransformation

class CapsuleViewHolder : ViewHolderVisitor {

    override val layout = R.layout.capsule_item

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        val capsule = item as Capsule
        with(binding as CapsuleItemBinding) {
            this.clickListener = if (capsule.totalFlights > 0) clickListener else AdapterClickListenerById { _, _ ->
                Toast.makeText(root.context, root.context.getString(R.string.capsule_not_fly_text), Toast.LENGTH_LONG).show()
            }
            this.capsule = capsule

            Picasso.get()
                .load(
                    when (capsule.type) {
                        CapsuleType.DRAGON1_0 -> R.drawable.dragon_1_0_foreground
                        CapsuleType.DRAGON1_1 -> R.drawable.dragon_1_1_foreground
                        CapsuleType.DRAGON2_0 -> R.drawable.dragon_2_0_foreground
                    }
                )
                .transform(CircleTransformation())
                .placeholder(R.drawable.loading_animation)
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
                if (capsule.totalFlights > 0) append(root.resources.getQuantityString(R.plurals.reuseCountString, capsule.totalFlights, capsule.totalFlights))
                if (capsule.landLandings > 0) append(root.context.getString(R.string.groundLandCountString, capsule.landLandings))
                if (capsule.waterLandings > 0) append(root.context.getString(R.string.waterLandCountString, capsule.waterLandings))
            }
        }
    }

    override fun acceptBinding(item: Any): Boolean = item is Capsule
}