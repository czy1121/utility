package me.reezy.cosmo.utility.context

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat

inline fun Context.colorStateList(@ColorRes resId: Int): ColorStateList? {
    return AppCompatResources.getColorStateList(this, resId)
}

inline fun Context.drawable(@DrawableRes resId: Int): Drawable? {
    return AppCompatResources.getDrawable(this, resId)
}



@SuppressLint("DiscouragedApi")
fun Context.dimen(key: String): Int {
    try {
        val resourceId = resources.getIdentifier(key, "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
    } catch (ignored: Resources.NotFoundException) {
    }
    return 0
}
