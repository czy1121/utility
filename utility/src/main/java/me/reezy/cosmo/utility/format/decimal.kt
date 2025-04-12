package me.reezy.cosmo.utility.format

import java.math.BigDecimal
import java.math.RoundingMode


/** 格式化十进制大数字("K", "M", "B", "T", "aa", "ab", "ac", "ad") */
fun BigDecimal.formatEn(scale: Int = 1): String {
    return format(BigDecimal("1000"), scale, arrayOf("K", "M", "B", "T", "aa", "ab", "ac", "ad"))
}

/** 格式化十进制大数字("万", "亿", "兆", "京") */
fun BigDecimal.formatCn(scale: Int = 1): String {
    return format(BigDecimal("10000"), scale, arrayOf("万", "亿", "兆", "京"))
}

private fun BigDecimal.format(base: BigDecimal, scale: Int = 1, units: Array<String>): String = try {
    var value = this
    var i = 0
    var unit = ""

    while (value >= base && i < units.size) {
        value = value.divide(base)
        unit = units[i++]
    }

    val plain = value.setScale(scale, RoundingMode.DOWN).toPlainString()

    "$plain$unit"
} catch (e: Exception) {
    e.printStackTrace()
    toPlainString()
}