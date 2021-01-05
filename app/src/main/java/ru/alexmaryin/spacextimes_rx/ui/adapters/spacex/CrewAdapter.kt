package ru.alexmaryin.spacextimes_rx.ui.adapters.spacex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder

class CrewAdapter: BaseAdapter<Crew>(arrayListOf()) {

    class ViewHolder(itemView: View) : DataViewHolder<Crew>(itemView) {
        override fun bind(item: Crew) {
            itemView.findViewById<TextView>(R.id.nameText).apply { text = item.name; background.alpha = 30 }

            itemView.findViewById<ImageView>(R.id.photoCrew).apply {
                Glide.with(this.context)
                    .load(item.image)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Crew> =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.crew_item, parent, false))
}