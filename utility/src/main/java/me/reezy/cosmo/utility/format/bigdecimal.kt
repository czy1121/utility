package me.reezy.cosmo.utility.format

import java.math.BigDecimal
import java.math.RoundingMode


/**
 * 格式化十进制大数字("K", "M", "B", "T", "aa", "ab", "ac", "ad")
 */
fun String.formatBigDecimal(): String {
    return try {
        val k = BigDecimal("1000")
        var v = BigDecimal(this)
        if (v < k) {
            return this
        }

        val units = listOf("K", "M", "B", "T", "aa", "ab", "ac", "ad")
        var i = 0
        var u: String
        do {
            v = v.divide(k)
            u = units[i++]
        } while(v > k && i < units.size)

        val p = v.setScale(1, RoundingMode.DOWN).toPlainString()

        return "$p$u"
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}