package ru.alexmaryin.spacextimes_rx.ui.adapters.spacex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Crew
import ru.alexmaryin.spacextimes_rx.databinding.CrewItemBinding
import ru.alexmaryin.spacextimes_rx.ui.adapters.BaseAdapter
import ru.alexmaryin.spacextimes_rx.ui.adapters.DataViewHolder

class CrewAdapter: BaseAdapter<Crew>(arrayListOf()) {

    class ViewHolder(private val binding: CrewItemBinding) : DataViewHolder<Crew>(binding) {

        override fun bind(item: Crew) {
            with (binding) {
                nameText.apply { text = item.name; background.alpha = 100 }
                photoCrew.apply {
                    Picasso.get()
                        .load(item.image)
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_broken_image)
                        .into(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<Crew> =
        LayoutInflater.from(parent.context).run { ViewHolder(DataBindingUtil.inflate(this, R.layout.crew_item, parent, false)) }
}