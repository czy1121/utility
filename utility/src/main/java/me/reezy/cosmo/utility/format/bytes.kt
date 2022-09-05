package me.reezy.cosmo.utility.format

import java.text.DecimalFormat


private const val BYTES_KB: Long = 1024
private const val BYTES_10KB: Long = 10240
private const val BYTES_100KB: Long = 102400
private const val BYTES_MB: Long = 1048576
private const val BYTES_10MB: Long = 10485760
private const val BYTES_100MB: Long = 104857600
private const val BYTES_GB: Long = 1073741824
private const val BYTES_10GB: Long = 10737418240
private const val BYTES_100GB: Long = 107374182400

private fun size(formatter: String, bytes: Double, unit: String): String {
    return DecimalFormat(formatter).format(bytes) + unit
}

fun Long.formatBytes(compact:Boolean = false): String = when(compact) {
    true -> when {
        this > BYTES_100GB -> "${this / BYTES_GB}GB"
        this > BYTES_10GB -> size("#.#", this * 1.0 / BYTES_GB, "GB")
        this > BYTES_GB -> size("#.##", this * 1.0 / BYTES_GB, "GB")
        this > BYTES_100MB -> "${this / BYTES_MB}MB"
        this > BYTES_10MB -> size("#.#", this * 1.0 / BYTES_MB, "MB")
        this > BYTES_MB -> size("#.##", this * 1.0 / BYTES_MB, "MB")
        this > BYTES_100KB -> "${this / BYTES_KB}KB"
        this > BYTES_10KB -> size("#.#", this * 1.0 / BYTES_KB, "KB")
        this > BYTES_KB -> size("#.##", this * 1.0 / BYTES_KB, "KB")
        else -> "${this}B"
    }
    else -> when {
        this > BYTES_GB -> size("#.##", this * 1.0 / BYTES_GB, "GB")
        this > BYTES_MB -> size("#.##", this * 1.0 / BYTES_MB, "MB")
        this > BYTES_KB -> size("#.##", this * 1.0 / BYTES_KB, "KB")
        else -> "${this}B"
    }
}
