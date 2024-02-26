package me.reezy.cosmo.utility.view

import android.annotation.SuppressLint
import android.graphics.Outline
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import me.reezy.cosmo.R
import me.reezy.cosmo.utility.context.resolveActivity

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
