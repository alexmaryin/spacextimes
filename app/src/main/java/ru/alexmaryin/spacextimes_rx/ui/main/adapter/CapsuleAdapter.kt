package ru.alexmaryin.spacextimes_rx.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.CapsuleType

class CapsuleAdapter(private val capsules: ArrayList<Capsule>): RecyclerView.Adapter<CapsuleAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(capsule: Capsule) {
            itemView.findViewById<TextView>(R.id.capsuleSerial).apply { text = capsule.serial }
            itemView.findViewById<TextView>(R.id.capsuleType).apply { text = when(capsule.type) {
                CapsuleType.DRAGON1_0 -> "Грузовой 1-й версии"
                CapsuleType.DRAGON1_1 -> "Грузовой версии 1.1"
                CapsuleType.DRAGON2_0 -> "Пилотируемый версии 2.0"
            } }
            itemView.findViewById<TextView>(R.id.capsuleStatus).apply { text = when(capsule.status) {
                CapsuleStatus.UNKNOWN -> "неизвестно"
                CapsuleStatus.ACTIVE -> "готова к полету"
                CapsuleStatus.RETIRED -> "на обслуживании"
                CapsuleStatus.DESTROYED -> "уничтожена"
            } }
            itemView.findViewById<TextView>(R.id.capsuleReused).apply {
                text = (if (capsule.reuseCount > 0) "летата ${capsule.reuseCount} раз" else "") +
                        (if (capsule.landLandings > 0) "\n${capsule.landLandings} посадок на землю" else "") +
                        (if (capsule.waterLandings > 0) "\n${capsule.waterLandings} посадок на воду" else "")
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
}