package ru.alexmaryin.spacextimes_rx.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

            val capsuleImage = itemView.findViewById<ImageView>(R.id.capluseImage)
            capsuleImage.setImageResource( when(capsule.type) {
                CapsuleType.DRAGON1_0 -> R.drawable.dragon1_0
                CapsuleType.DRAGON1_1 -> R.drawable.dragon1_1
                CapsuleType.DRAGON2_0 -> R.drawable.dragon2_0
            })

            itemView.findViewById<TextView>(R.id.capsuleStatus).apply { when(capsule.status) {
                    CapsuleStatus.UNKNOWN -> {
                        text = "неизвестно"
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_unknown, 0, 0, 0)
                        capsuleImage.alpha = 0.5F
                    }
                    CapsuleStatus.ACTIVE -> {
                        text = "активна"
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_active, 0, 0, 0)
                        capsuleImage.alpha = 1F
                    }
                    CapsuleStatus.RETIRED -> {
                        text = "на обслуживании"
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_retired, 0, 0, 0)
                        capsuleImage.alpha = 0.8F
                    }
                    CapsuleStatus.DESTROYED -> {
                        text = "уничтожена"
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_destroyed, 0, 0,  0)
                        capsuleImage.alpha = 0.4F
                    }
                }
            }

            itemView.findViewById<TextView>(R.id.capsuleReused).apply {
                text = buildString {
                    if (capsule.reuseCount > 0) append("летала ${capsule.reuseCount} раз")
                    if (capsule.landLandings > 0) append("\nприземлений на землю: ${capsule.landLandings}")
                    if (capsule.waterLandings > 0) append("\nприземлений на воду: ${capsule.waterLandings}")
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