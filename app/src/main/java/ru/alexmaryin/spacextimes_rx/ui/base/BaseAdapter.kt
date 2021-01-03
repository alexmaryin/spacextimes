package ru.alexmaryin.spacextimes_rx.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(private val items: ArrayList<T>) : RecyclerView.Adapter<DataViewHolder<T>>() {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<T>

    override fun onBindViewHolder(holder: DataViewHolder<T>, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun addData(list: List<T>) {
        items.clear()
        items.addAll(list)
    }

    fun clear() {
        items.clear()
    }
}

abstract class DataViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}