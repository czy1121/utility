package me.reezy.cosmo.utility.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.ceil
import kotlin.math.pow

fun Bitmap.compressCircularly(format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, maxSize: Int = 500 * 1024, maxCount: Int = 10): ByteArray = ByteArrayOutputStream().use {
    for (i in 1..maxCount) {
        it.reset()
        compress(format, (100 * 0.8.pow(i.toDouble())).toInt(), it)
        if (it.size() <= maxSize) {
            break
        }
    }
    it.toByteArray()
}

fun Bitmap.compressToFile(file: File, maxSize: Int = 500 * 1024): File {
    val bytes = compressCircularly(Bitmap.CompressFormat.JPEG, maxSize)

    file.parentFile?.mkdirs()
    file.delete()
    file.writeBytes(bytes)

    return file
}

fun Bitmap.toByteArray(format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG): ByteArray {
    val os = ByteArrayOutputStream()
    compress(format, 100, os)
    return os.toByteArray()
}

fun decodeScaledOptions(bytes: ByteArray, maxWidth: Int = 1080, maxHeight: Int = 1080): BitmapFactory.Options {

    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    val sw = ceil(options.outWidth / maxWidth.toDouble()).toInt()
    val sh = ceil(options.outHeight / maxHeight.toDouble()).toInt()
    if (sh > 1 && sw > 1) {
        options.inSampleSize = kotlin.math.max(sw, sh)
    } else if (sh > 1) {
        options.inSampleSize = sh
    } else if (sw > 1) {
        options.inSampleSize = sw
    }
    options.inJustDecodeBounds = false
    return options
}