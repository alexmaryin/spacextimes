package ru.alexmaryin.spacextimes_rx.ui.adapters

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(private val items: ArrayList<T>, private val clickListener: AdapterClickListenerById) : RecyclerView.Adapter<DataViewHolder<T>>() {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<T>

    override fun onBindViewHolder(holder: DataViewHolder<T>, position: Int) = holder.bind(items[position], clickListener)

    override fun getItemCount(): Int = items.size

    fun addData(list: List<T>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}

abstract class DataViewHolder<T>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T, clickListener: AdapterClickListenerById)
}