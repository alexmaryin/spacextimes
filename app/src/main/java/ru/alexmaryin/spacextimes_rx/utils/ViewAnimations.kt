package ru.alexmaryin.spacextimes_rx.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

fun View.crossFade(from: View, duration: Int) {
    this.apply {
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(duration.toLong())
            .setListener(null)
    }
    from.animate()
        .alpha(0f)
        .setDuration(duration.toLong())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                from.visibility = View.GONE
            }
        })
}