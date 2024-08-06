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
import androidx.core.view.updatePadding
import me.reezy.cosmo.R
import me.reezy.cosmo.utility.context.navigationBarHeight
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

/**
 * 更新视图的边距
 * */
inline fun View.updateMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val params = layoutParams
    if (params is MarginLayoutParams) {
        left?.let {
            params.leftMargin = it
        }
        top?.let {
            params.topMargin = it
        }
        right?.let {
            params.rightMargin = it
        }
        bottom?.let {
            params.bottomMargin = it
        }
        layoutParams = params
    }
}

/**
 * 更新视图的宽高
 * */
inline fun View.updateLayout(width: Int? = null, height: Int? = null) {
    val params = layoutParams
    width?.let {
        params.width = it
    }
    height?.let {
        params.height = it
    }
    layoutParams = params
}


/**
 * 适配状态栏
 * */
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

/**
 * 适配导航栏
 * */
fun View.fitsNavigationBar(useMargin: Boolean = false) {
    val barHeight = navigationBarHeight
    when {
        useMargin -> {
            val lp = layoutParams
            if (lp is MarginLayoutParams) {
                updateMargin(bottom = lp.bottomMargin + barHeight)
            }
        }

        layoutParams.height < 0 -> {
            updatePadding(bottom = paddingBottom + barHeight)
        }

        else -> {
            updateLayout(height = layoutParams.height + barHeight)
            updatePadding(bottom = paddingBottom + barHeight)
        }
    }
}