package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders

import android.os.Build
import androidx.databinding.ViewDataBinding
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.databinding.LaunchItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHolderVisitor
import ru.alexmaryin.spacextimes_rx.utils.getColorIdFromAttr

class LaunchesViewHolder : ViewHolderVisitor {

    override val layout = R.layout.launch_item

    override fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById) {
        val launch = item as Launch
        with(binding as LaunchItemBinding) {
            this.clickListener = clickListener
            this.launch = launch

            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                box.setBackgroundColor(
                    root.resources.getColor(
                        when {
                            launch.upcoming || launch.success == null -> root.context.getColorIdFromAttr(R.attr.colorOnPrimary)
                            launch.success -> R.color.success_color
                            else -> R.color.fail_color
                        }, root.context.theme
                    )
                )
            } else box.setBackgroundColor(
                root.resources.getColor(
                    when {
                        launch.upcoming || launch.success == null -> root.context.getColorIdFromAttr(R.attr.colorOnPrimary)
                        launch.success -> R.color.success_color
                        else -> R.color.fail_color
                    }
                )
            )
        }
    }

    override fun acceptBinding(item: Any): Boolean = item is Launch
}
