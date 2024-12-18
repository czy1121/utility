@file:Suppress("DEPRECATION")

package me.reezy.cosmo.utility.context

import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

val WindowManager.windowHeight: Int
    get() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> currentWindowMetrics.bounds.height()
        else -> DisplayMetrics().apply { defaultDisplay.getRealMetrics(this) }.heightPixels
    }

val WindowManager.windowWidth: Int
    get() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> currentWindowMetrics.bounds.width()
        else -> DisplayMetrics().apply { defaultDisplay.getRealMetrics(this) }.widthPixels
    }

inline val Window.windowHeight: Int get() = windowManager.windowHeight
inline val Window.windowWidth: Int get() = windowManager.windowWidth

inline val View.windowHeight: Int get() = context.getSystemService(WindowManager::class.java).windowHeight
inline val View.windowWidth: Int get() = context.getSystemService(WindowManager::class.java).windowWidth

val View.statusBarInset: Int get() = ViewCompat.getRootWindowInsets(this)?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0

val View.navigationBarInset: Int get() = ViewCompat.getRootWindowInsets(this)?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0


val View.statusBarHeight: Int get() = ViewCompat.getRootWindowInsets(this)?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: context.dimen("status_bar_height")

val View.navigationBarHeight: Int get() = ViewCompat.getRootWindowInsets(this)?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom ?: context.dimen("navigation_bar_height")


var Window.isStatusBarLight: Boolean
    get() = WindowCompat.getInsetsController(this, decorView).isAppearanceLightStatusBars
    set(value) {
        WindowCompat.getInsetsController(this, decorView).isAppearanceLightStatusBars = value
    }

var Window.isNavigationBarLight: Boolean
    get() = WindowCompat.getInsetsController(this, decorView).isAppearanceLightNavigationBars
    set(value) {
        WindowCompat.getInsetsController(this, decorView).isAppearanceLightNavigationBars = value
    }

fun Window.setLayoutInFullscreen(fullscreen: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        attributes = attributes.apply {
            layoutInDisplayCutoutMode = when {
                !fullscreen -> WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
                else -> WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }
    }
    WindowCompat.setDecorFitsSystemWindows(this, !fullscreen)
}

// 进入沉浸式
fun Window.hideSystemBars(barsBehavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE) {
    val ic = WindowCompat.getInsetsController(this, decorView)
    // 重新显示的方式
    ic.systemBarsBehavior = barsBehavior
    // 隐藏系统栏
    ic.hide(WindowInsetsCompat.Type.systemBars())
}

// 退出沉浸式
fun Window.showSystemBars() {
    WindowCompat.getInsetsController(this, decorView).show(WindowInsetsCompat.Type.systemBars())
}


