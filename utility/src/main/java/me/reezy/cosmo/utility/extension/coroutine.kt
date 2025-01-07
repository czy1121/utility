package me.reezy.cosmo.utility.extension

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withResumed
import androidx.lifecycle.withStateAtLeast
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

fun LifecycleOwner.loop(state: Lifecycle.State = Lifecycle.State.RESUMED, interval: Long = 1000, action: suspend () -> Unit) = lifecycleScope.launch {
    repeatOnLifecycle(state) {
        while (true) {
            try {
                action()
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
            delay(interval)
        }
    }
}

/**
 * 在 [Lifecycle.State] 每次进入 [state] 时运行 [block]
 * */
fun LifecycleOwner.launchOn(state: Lifecycle.State = Lifecycle.State.RESUMED, block: suspend CoroutineScope.() -> Unit) = lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(state, block)
}


/**
 * 在 [Lifecycle.State] 至少为 [state] 的时运行 [block]
 * */
fun LifecycleOwner.launchWith(state: Lifecycle.State = Lifecycle.State.RESUMED, block: () -> Unit) = lifecycleScope.launch {
    lifecycle.withStateAtLeast(state, block)
}
