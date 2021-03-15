package ru.alexmaryin.spacextimes_rx.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

const val LONG_ANIMATION = 1000

infix fun View.crossFadeWith(from: View) {
    from.alpha = 1f
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .setDuration(LONG_ANIMATION.toLong())
        .alpha(1f)
        .setListener(null)
    from.animate()
        .setDuration(LONG_ANIMATION.toLong())
        .alpha(0f)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                from.visibility = View.GONE
            }
        })
}