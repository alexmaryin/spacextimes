package ru.alexmaryin.spacextimes_rx.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId

class BaseListAdapter(
    private val clickListener: AdapterClickListenerById,
    private val viewHoldersManager: ViewHoldersManager
) : ListAdapter<HasStringId, BaseListAdapter.DataViewHolder>(BaseDiffCallback()) {

    inner class DataViewHolder(
        val binding: ViewDataBinding,
        private val holder: ViewHolderVisitor) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HasStringId, clickListener: AdapterClickListenerById) {
            holder.bind(binding, item, clickListener)
            binding.root.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.recycler_item_arrive)
        }
    }

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        LayoutInflater.from(parent.context).run {
            val holder = viewHoldersManager.getViewHolder(viewType)
            DataViewHolder(DataBindingUtil.inflate(this, holder.layout, parent, false), holder)
        }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(getItem(position), clickListener)

    override fun getItemViewType(position: Int): Int = viewHoldersManager.getItemType(getItem(position))
}

class BaseDiffCallback : DiffUtil.ItemCallback<HasStringId>() {
    override fun areItemsTheSame(oldItem: HasStringId, newItem: HasStringId): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: HasStringId, newItem: HasStringId): Boolean = oldItem == newItem
}