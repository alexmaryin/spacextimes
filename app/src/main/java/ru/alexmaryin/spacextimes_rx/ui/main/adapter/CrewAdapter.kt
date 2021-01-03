package ru.alexmaryin.spacextimes_rx.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Crew

class CrewAdapter(private val crew: ArrayList<Crew>): RecyclerView.Adapter<CrewAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(crewMember: Crew) {
            itemView.findViewById<TextView>(R.id.nameText).apply { text = crewMember.name; background.alpha = 30 }

            itemView.findViewById<ImageView>(R.id.photoCrew).apply {
                Glide.with(this.context)
                    .load(crewMember.image)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(this) }
        }
    }

    fun addData(list: List<Crew>) {
        crew.clear()
        crew.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.crew_item, parent, false))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(crew[position])

    override fun getItemCount(): Int = crew.size

    fun clear() {
        crew.clear()
    }
}