package ru.alexmaryin.spacextimes_rx.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.exp
import kotlin.math.sin

const val LONG_ANIMATION = 1000

infix fun View.crossFadeFrom(from: View) {
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

infix fun View.replaceBy(view: View) {
    view.visibility = View.VISIBLE
    visibility = View.GONE
}

val shakeInterpolator = TimeInterpolator { delta ->
    val freq = 6f
    val decay = 2f
    val raw = sin(freq * delta * Math.PI)
    (raw * exp((-delta * decay).toDouble())).toFloat()
}

fun RecyclerView.addItemShaker(position: Int) {

    val shaker = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                recyclerView.layoutManager?.findViewByPosition(position)?.animate()!!
                    .xBy(-100f)
                    .setInterpolator(shakeInterpolator)
                    .setDuration(500)
                    .start()
                recyclerView.removeOnScrollListener(this)
            }
        }
    }

    val scroller = object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference() = SNAP_TO_START
    }.apply { targetPosition = position }

    // first, scroll a little down to trigger shake if item already on top
    scrollBy(0, 10)
    // add shake animation after scrolling
    addOnScrollListener(shaker)
    // scroll to next launch
    layoutManager?.startSmoothScroll(scroller)
}