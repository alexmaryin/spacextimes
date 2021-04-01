package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launches
import ru.alexmaryin.spacextimes_rx.databinding.LaunchItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.*
import ru.alexmaryin.spacextimes_rx.utils.downloadByLongClickListener
import ru.alexmaryin.spacextimes_rx.utils.getColorIdFromAttr

class LaunchesAdapter(clickListener: AdapterClickListenerById) : BaseListAdapter(clickListener) {

    class ViewHolder(private val binding: LaunchItemBinding) : DataViewHolder(binding) {

        override fun bind(item: DataItem, clickListener: AdapterClickListenerById) {
            val launch = item.asData<Launches>()!!
            with(binding) {
                this.clickListener = clickListener
                this.launch = launch

                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    box.setBackgroundColor(
                        root.resources.getColor(
                            when {
                                launch.upcoming -> root.context.getColorIdFromAttr(R.attr.colorOnPrimary)
                                launch.success -> R.color.success_color
                                else -> R.color.fail_color
                            }, root.context.theme
                        )
                    )
                else box.setBackgroundColor(
                    root.resources.getColor(
                        when {
                            launch.upcoming -> root.context.getColorIdFromAttr(R.attr.colorOnPrimary)
                            launch.success -> R.color.success_color
                            else -> R.color.fail_color
                        }
                    )
                )

                patchImage.setOnLongClickListener(downloadByLongClickListener(
                    launch.links.patch.large ?: launch.rocket.images[0],
                    "${launch.name}_patch.jpg")
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder = if (viewType == BODY_TYPE)
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.launch_item, parent, false)) }
    else super.onCreateViewHolder(parent, viewType)
}