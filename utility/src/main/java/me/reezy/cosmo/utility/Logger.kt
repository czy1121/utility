package me.reezy.cosmo.utility

import android.os.Bundle
import android.util.Log


object Logger {

    fun interface Writer {
        fun write(priority: Int, tag: String, msg: String)
    }

    private var logTag: String = "OoO"
    private var logWriter: Writer = Writer { priority, tag, msg ->
        Log.println(priority, tag, msg)
    }

    fun setTag(tag: String) {
        logTag = tag
    }

    fun setWriter(writer: Writer) {
        logWriter = writer
    }

    fun info(message: Any, throwable: Throwable? = null) {
        logWriter.write(Log.INFO, logTag, toString(message) + '\n' + Log.getStackTraceString(throwable))
    }

    fun warn(message: Any, throwable: Throwable? = null) {
        logWriter.write(Log.WARN, logTag, toString(message) + '\n' + Log.getStackTraceString(throwable))
    }

    fun error(message: Any, throwable: Throwable? = null) {
        logWriter.write(Log.ERROR, logTag, toString(message) + '\n' + Log.getStackTraceString(throwable))
    }

    private fun toString(o: Any) = when (o) {
        is String -> o
        is Throwable -> Log.getStackTraceString(o)
        is Bundle -> {
            val map = mutableMapOf<String, Any?>()
            o.keySet().forEach {
                map[it] = o.get(it)
            }
            map.toString()
        }
        else -> o.toString()
    }
}