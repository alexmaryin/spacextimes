package ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.model.Dragon
import ru.alexmaryin.spacextimes_rx.data.model.parts.Shield

object DragonAdapters {
    @JvmStatic
    @BindingAdapter("dragonShortStatus")
    fun postStatusLine(view: TextView, dragon: Dragon?) {
        dragon?.let {
            with(view) {
                setCompoundDrawablesRelativeWithIntrinsicBounds(if (dragon.isActive) R.drawable.ic_active else R.drawable.ic_destroyed, 0, 0, 0)
                text = resources.getString(
                    R.string.dragon_status_line_string,
                    dragon.launchPayloadMass.kg / 1000,
                    dragon.launchPayloadVolume.inMeters,
                    dragon.returnPayloadMass.kg / 1000,
                    dragon.returnPayloadVolume.inMeters
                )
            }
        }
    }

    @JvmStatic
    @BindingAdapter("dragonSize")
    fun postSizeLine(view: TextView, dragon: Dragon?) {
        dragon?.let {
            view.text = view.resources.getString(
                R.string.dragon_size_string,
                dragon.heightWithTrunk.meters,
                dragon.diameter.meters,
                dragon.pressurizedCapsule.payload.inMeters
            )
        }
    }

    @JvmStatic
    @BindingAdapter("heatShield")
    fun postHeatShield(view: TextView, shield: Shield?) {
        shield?.let {
            view.text = view.resources.getString(
                R.string.heat_shield_string,
                shield.size,
                shield.material,
                shield.temperature,
                shield.developPartner
            )
        }
    }

    @JvmStatic
    @BindingAdapter("crewCapacity")
    fun postCrewCapacity(view: TextView, capacity: Int) {
        if (capacity > 0) {
            view.visibility = View.VISIBLE
            view.text = view.resources.getString(R.string.crew_capacity_string, "\uD83D\uDC64".repeat(capacity))
        } else view.visibility = View.GONE
    }
}