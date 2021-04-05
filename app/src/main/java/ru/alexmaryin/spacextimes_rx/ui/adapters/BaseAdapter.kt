package ru.alexmaryin.spacextimes_rx.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

class BaseListAdapter(
    private val clickListener: AdapterClickListenerById,
    private val adaptersManager: AdaptersManager
) : ListAdapter<HasStringId, BaseListAdapter.DataViewHolder>(BaseDiffCallback()) {

    private lateinit var holder: AdapterItem

    inner class DataViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HasStringId, clickListener: AdapterClickListenerById) =
            holder.adapter.bind(binding, item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        LayoutInflater.from(parent.context).run {
            holder = adaptersManager.getAdapter(viewType)
            DataViewHolder(DataBindingUtil.inflate(this, holder.layout, parent, false))
        }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(getItem(position), clickListener)

    override fun getItemViewType(position: Int): Int = adaptersManager.getItemType(getItem(position))
}

class BaseDiffCallback : DiffUtil.ItemCallback<HasStringId>() {
    override fun areItemsTheSame(oldItem: HasStringId, newItem: HasStringId): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: HasStringId, newItem: HasStringId): Boolean = oldItem == newItem
}