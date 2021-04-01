package ru.alexmaryin.spacextimes_rx.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.databinding.RecyclerHeaderItemBinding

const val HEADER_TYPE = 0
const val BODY_TYPE = 1

open class BaseListAdapter(
    private val clickListener: AdapterClickListenerById,
) : ListAdapter<DataItem, DataViewHolder>(BaseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder = if (viewType == HEADER_TYPE)
        HeaderViewHolder.from(parent) else throw TypeCastException("Unknown recycler item type!")

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(getItem(position), clickListener)

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).id) {
            "header" -> HEADER_TYPE
            else -> BODY_TYPE
        }
    }
}

abstract class DataViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: DataItem, clickListener: AdapterClickListenerById)
}

class BaseDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
        (oldItem is DataItem.BodyItem<*> && newItem is DataItem.BodyItem<*> && oldItem.item == newItem.item)
                ||(oldItem is DataItem.Header && newItem is DataItem.Header && oldItem.header == newItem.header)
}

class HeaderViewHolder(private val binding: RecyclerHeaderItemBinding) : DataViewHolder(binding) {

    companion object {
        fun from(parent: ViewGroup) = LayoutInflater.from(parent.context).run {
            HeaderViewHolder(DataBindingUtil.inflate(this, R.layout.recycler_header_item, parent, false))
        }
    }

    override fun bind(item: DataItem, clickListener: AdapterClickListenerById) {
        binding.headerItem = (item as DataItem.Header).header
    }
}