package me.reezy.cosmo.utility.extension

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import kotlin.math.pow

fun Bitmap.compressCircularly(maxSize: Int, format: Bitmap.CompressFormat): ByteArray = ByteArrayOutputStream().use {
    for (i in 1..10) {
        compress(format, (100 * 0.8.pow(i.toDouble())).toInt(), it)
//            Log.e("OoO.compress", "size = " + it.size())
        if (it.size() <= maxSize) {
            break
        } else {
            it.reset()
        }
    }
    it.toByteArray()
}