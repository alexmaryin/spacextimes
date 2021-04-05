package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import android.os.Build
import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launches
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.databinding.LaunchItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterVisitor
import ru.alexmaryin.spacextimes_rx.utils.downloadByLongClickListener
import ru.alexmaryin.spacextimes_rx.utils.getColorIdFromAttr

class LaunchesViewHolder : AdapterVisitor {

    override fun bind(binding: ViewDataBinding, item: HasStringId, clickListener: AdapterClickListenerById) {
        val launch = item as Launches
        with(binding as LaunchItemBinding) {
            this.clickListener = clickListener
            this.launch = launch

            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                box.setBackgroundColor(
                    root.resources.getColor(
                        when {
                            launch.upcoming -> root.context.getColorIdFromAttr(R.attr.colorOnPrimary)
                            launch.success -> R.color.success_color
                            else -> R.color.fail_color
                        }, root.context.theme
                    )
                )
            } else box.setBackgroundColor(
                root.resources.getColor(
                    when {
                        launch.upcoming -> root.context.getColorIdFromAttr(R.attr.colorOnPrimary)
                        launch.success -> R.color.success_color
                        else -> R.color.fail_color
                    }
                )
            )

            patchImage.setOnLongClickListener(
                downloadByLongClickListener(
                    launch.links.patch.large ?: launch.rocket.images[0],
                    "${launch.name}_patch.jpg"
                )
            )
        }
    }

    override fun acceptVisitor(item: HasStringId): Boolean = item is Launches
}