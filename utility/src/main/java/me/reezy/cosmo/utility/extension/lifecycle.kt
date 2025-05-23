package me.reezy.cosmo.utility.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withStateAtLeast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * 在 [Lifecycle.State] 每次进入 [state] 时开始循环，离开 [state] 时退出循环
 * */
fun LifecycleOwner.loopOn(state: Lifecycle.State = Lifecycle.State.RESUMED, interval: Long = 1000, block: suspend () -> Unit) = lifecycleScope.launch {
    repeatOnLifecycle(state) {
        while (true) {
            try {
                block()
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
fun LifecycleOwner.repeatOn(state: Lifecycle.State = Lifecycle.State.RESUMED, block: suspend CoroutineScope.() -> Unit) = lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(state, block)
}


/**
 * 在 [Lifecycle.State] 至少为 [state] 时运行 [block]
 * */
fun LifecycleOwner.launchWith(state: Lifecycle.State = Lifecycle.State.RESUMED, block: () -> Unit) = lifecycleScope.launch {
    lifecycle.withStateAtLeast(state, block)
}
