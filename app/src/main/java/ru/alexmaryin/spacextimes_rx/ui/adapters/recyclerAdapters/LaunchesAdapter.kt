package ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerAdapters

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.databinding.LaunchItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseListAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder
import ru.alexmaryin.spacextimes_rx.utils.getColorIdFromAttr

class LaunchesAdapter(clickListener: AdapterClickListenerById) : BaseListAdapter<Launch>(arrayListOf(), clickListener) {

    class ViewHolder(private val binding: LaunchItemBinding) : DataViewHolder<Launch>(binding) {

        override fun bind(item: Launch, clickListener: AdapterClickListenerById) {
            with(binding) {
                this.clickListener = clickListener
                launch = item

                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    box.setBackgroundColor(root.resources.getColor( when {
                        item.upcoming -> root.context.getColorIdFromAttr(R.attr.colorOnPrimary)
                        item.success -> R.color.success_color
                        else -> R.color.fail_color
                    }, root.context.theme))
                else box.setBackgroundColor(root.resources.getColor( when {
                    item.upcoming -> root.context.getColorIdFromAttr(R.attr.colorOnPrimary)
                    item.success -> R.color.success_color
                    else -> R.color.fail_color
                }))

                patchImage.setOnLongClickListener {
                    AlertDialog.Builder(it.context)
                        .setTitle(it.context.getString(R.string.saving_title_string))
                        .setMessage(it.context.getString(R.string.save_image_dialog_string))
                        .setPositiveButton(it.context.getString(R.string.agreeText)) { dialog, _ ->
                            val request = DownloadManager.Request(Uri.parse(item.links.patch.large)).apply {
                                setTitle(it.context.getString(R.string.saving_title_string))
                                setDestinationInExternalPublicDir (Environment.DIRECTORY_PICTURES, "${item.name}_patch.jpg")
                                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            }
                            (it.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).apply { enqueue(request) }
                            dialog.dismiss()
                        }
                        .setNegativeButton(it.context.getString(R.string.cancelText)) { dialog, _ -> dialog.dismiss() }
                        .show()
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Launch> =
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.launch_item, parent, false)) }
}