package me.reezy.cosmo.utility.extension

import android.annotation.SuppressLint
import android.widget.TextView
import kotlinx.coroutines.*


fun CoroutineScope.countdown(countdown: Long, onTick: (Long) -> Unit = {}, onDone: () -> Unit) = launch(Dispatchers.Default) {
    (countdown downTo 1).forEach {
        withContext(Dispatchers.Main) {
            onTick(it)
        }
        delay(1000)
    }
    withContext(Dispatchers.Main) {
        onDone()
    }
}

@SuppressLint("SetTextI18n")
fun CoroutineScope.countdown(view: TextView, time: Long = 60, suffixText: String = "s"): Job {
    val doneText = view.text
    view.isEnabled = false
    return countdown(time, onTick = {
        view.text = "$it$suffixText"
    }) {
        view.isEnabled = true
        view.text = doneText
    }
}
