package me.reezy.cosmo.utility.view

import android.view.View
import me.reezy.cosmo.R

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
