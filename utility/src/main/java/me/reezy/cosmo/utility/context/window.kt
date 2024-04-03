@file:Suppress("DEPRECATION")

package me.reezy.cosmo.utility.context

import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

val WindowManager.windowHeight: Int get() =  when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> currentWindowMetrics.bounds.height()
    else -> DisplayMetrics().apply { defaultDisplay.getRealMetrics(this) }.heightPixels
}

val WindowManager.windowWidth: Int get() =  when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> currentWindowMetrics.bounds.width()
    else -> DisplayMetrics().apply { defaultDisplay.getRealMetrics(this) }.widthPixels
}


inline val View.windowHeight: Int get() = context.getSystemService(WindowManager::class.java).windowHeight
inline val View.windowWidth: Int get() = context.getSystemService(WindowManager::class.java).windowWidth

val View.statusBarHeight: Int get() = ViewCompat.getRootWindowInsets(this)?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: context.dimen("status_bar_height")

val View.navigationBarHeight: Int get() = ViewCompat.getRootWindowInsets(this)?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom  ?: context.dimen("navigation_bar_height")
