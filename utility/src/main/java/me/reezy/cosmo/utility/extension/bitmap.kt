package me.reezy.cosmo.utility.extension

import android.content.Context
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
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