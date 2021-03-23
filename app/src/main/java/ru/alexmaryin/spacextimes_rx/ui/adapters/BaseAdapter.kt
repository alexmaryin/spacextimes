package ru.alexmaryin.spacextimes_rx.ui.adapters

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

abstract class BaseListAdapter<T : HasStringId>(
    private val items: ArrayList<T>,
    private val clickListener: AdapterClickListenerById
) : ListAdapter<T, DataViewHolder<T>>(BaseDiffCallback()) {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<T>

    override fun onBindViewHolder(holder: DataViewHolder<T>, position: Int) = holder.bind(getItem(position), clickListener)
}

abstract class DataViewHolder<T>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T, clickListener: AdapterClickListenerById)
}

class BaseDiffCallback<T : HasStringId> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = newItem == oldItem
}