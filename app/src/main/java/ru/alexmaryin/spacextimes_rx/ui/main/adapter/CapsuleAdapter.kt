package ru.alexmaryin.spacextimes_rx.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleType

class CapsuleAdapter(private val capsules: ArrayList<Capsule>): RecyclerView.Adapter<CapsuleAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(capsule: Capsule) {
            itemView.findViewById<TextView>(R.id.capsuleSerial).apply { text = capsule.serial }

            /*itemView.findViewById<TextView>(R.id.capsuleType).apply { text = when(capsule.type) {
                CapsuleType.DRAGON1_0 -> "Грузовой 1-й версии"
                CapsuleType.DRAGON1_1 -> "Грузовой версии 1.1"
                CapsuleType.DRAGON2_0 -> "Пилотируемый версии 2.0"
            } }*/

            itemView.findViewById<LinearLayoutCompat>(R.id.container).background = when(capsule.type) {
                CapsuleType.DRAGON1_0 -> ContextCompat.getDrawable(itemView.context, R.mipmap.dragon_1_0)
                CapsuleType.DRAGON1_1 -> ContextCompat.getDrawable(itemView.context, R.mipmap.dragon_1_1)
                CapsuleType.DRAGON2_0 -> ContextCompat.getDrawable(itemView.context, R.mipmap.dragon_2_0)
            }!!.apply { alpha = 40 }

            itemView.findViewById<TextView>(R.id.capsuleStatus).apply { when(capsule.status) {
                    CapsuleStatus.UNKNOWN -> {
                        text = "неизвестно"
                        background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_unknown)
                    }
                    CapsuleStatus.ACTIVE -> {
                        text = "готова к полету"
                        background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_active)
                    }
                    CapsuleStatus.RETIRED -> {
                        text = "на обслуживании"
                        background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_retired)
                    }
                    CapsuleStatus.DESTROYED -> {
                        text = "уничтожена"
                        background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_destroyed)
                    }
                }
            }

            itemView.findViewById<TextView>(R.id.capsuleReused).apply {
                text = buildString {
                    if (capsule.reuseCount > 0) append("летата ${capsule.reuseCount} раз")
                    if (capsule.landLandings > 0) append(", ${capsule.landLandings} посадок на землю")
                    if (capsule.waterLandings > 0) append(", ${capsule.waterLandings} посадок на воду")
                }
            }

            itemView.findViewById<TextView>(R.id.capsuleUpdate).apply { text = capsule.lastUpdate }
        }
    }

    fun addData(list: List<Capsule>) {
        capsules.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.capsule_item, parent, false))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(capsules[position])

    override fun getItemCount(): Int = capsules.size

    fun clear() {
        capsules.clear()
        notifyDataSetChanged()
    }
}