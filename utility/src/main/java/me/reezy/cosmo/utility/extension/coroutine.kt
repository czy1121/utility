package me.reezy.cosmo.utility.extension

import kotlinx.coroutines.*


fun CoroutineScope.countdown(countdown: Long, onTick: (Long) -> Unit = {}, onDone: () -> Unit) {
    launch(Dispatchers.Default) {
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
}

//@SuppressLint("SetTextI18n")
//fun CoroutineScope.countdown(view: TextView, time: Long = 60) {
//    view.isEnabled = false
//    countdown(time, onTick = {
//        view.text = "${it}s"
//    }) {
//        view.isEnabled = true
//        view.text = "重新获取"
//    }
//}