@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.utility.view

import android.annotation.SuppressLint
import android.graphics.Outline
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewOutlineProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.ancestors
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import me.reezy.cosmo.R
import me.reezy.cosmo.utility.context.resolveActivity
import me.reezy.cosmo.utility.context.statusBarHeight

fun View.throttleClick(throttleTime: Long = 1000, block: (View) -> Unit) {
    if (throttleTime < 1) {
        setOnClickListener(block)
    } else {
        setOnClickListener {
            val tag = it.getTag(R.id.tag_last_click_time)
            val last = if (tag is Long) tag else 0L
            val now = System.currentTimeMillis()
            if (now - last > throttleTime) {
                block(it)
                it.setTag(R.id.tag_last_click_time, now)
            }
        }
    }
}

fun View.enable(value: Boolean, disableAlpha: Float = 0.5f) {
    isEnabled = value
    alpha = if (value) 1f else disableAlpha
}

inline fun <reified T : ViewGroup> View.findAncestor() = ancestors.find { it is T } as? T

fun View.setOutlineCornerRadius(radius: Int) {
    if (radius > 0) {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, radius.toFloat())
            }
        }
        clipToOutline = true
    } else {
        clipToOutline = false
    }
}


@SuppressLint("ClickableViewAccessibility")
fun View.hideSoftInputOnTouchOutside(views: Array<View>) {
    val rect = Rect()
    setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_MOVE && event.historySize > 1) {
            val isTouchInside = views.any {
                it.getGlobalVisibleRect(rect)
                rect.contains(event.rawX.toInt(), event.rawY.toInt())
            }
            if (!isTouchInside) {
                v.context.resolveActivity()?.window?.let {
                    WindowCompat.getInsetsController(it, v).hide(WindowInsetsCompat.Type.ime())
                }
            }
        }
        false
    }
}

inline fun View.updateMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        left?.let {
            leftMargin = it
        }
        top?.let {
            topMargin = it
        }
        right?.let {
            rightMargin = it
        }
        bottom?.let {
            bottomMargin = it
        }
    }
}

inline fun View.updateLayout(width: Int? = null, height: Int? = null) {
    updateLayoutParams<ViewGroup.LayoutParams> {
        width?.let {
            this.width = it
        }
        height?.let {
            this.height = it
        }
    }
}

// 适配状态栏
fun View.fitsStatusBar(useMargin: Boolean = false) {
    val barHeight = statusBarHeight
    when {
        useMargin -> {
            val lp = layoutParams
            if (lp is MarginLayoutParams) {
                updateMargin(top = lp.topMargin + barHeight)
            }
        }
        layoutParams.height < 0 -> {
            updatePadding(top = paddingTop + barHeight)
        }
        else -> {
            updateLayout(height = layoutParams.height + barHeight)
            updatePadding(top = paddingTop + barHeight)
        }
    }
}