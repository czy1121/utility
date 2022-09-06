package me.reezy.cosmo.utility

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService


object SoftInput {
    fun isActive(context: Context): Boolean {
        return context.getSystemService<InputMethodManager>()?.isActive ?: false
    }

    fun show(view: View, flags: Int = 0) {
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.context.getSystemService<InputMethodManager>()?.showSoftInput(view, flags)
    }

    fun hide(view: View, flags: Int = 0) {
        view.clearFocus()
        view.context.getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(view.windowToken, flags)
    }

    // 触摸视图 focuses 之外的区域时隐藏软键盘
    fun handleTouchOutsideEvent(event: MotionEvent, focuses: Array<View>, useMoveAction: Boolean = true) {
        if (checkEvent(event, useMoveAction)) {
            for (view in focuses) {
                if (checkOutside(view, event)) {
                    hide(view)
                }
            }
        }
    }

    class OnTouchOutsideListener(private val focuses: Array<View>, private val useMoveAction: Boolean = true) : View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            handleTouchOutsideEvent(event, focuses, useMoveAction)
            return false
        }
    }

    private fun checkEvent(event: MotionEvent, useMoveAction: Boolean): Boolean {
        return if (useMoveAction) event.action == MotionEvent.ACTION_MOVE && event.historySize > 1 else event.action == MotionEvent.ACTION_DOWN
    }

    private fun checkOutside(view: View, event: MotionEvent): Boolean {
        if (view.isFocused) {
            val rect = Rect()
            view.getGlobalVisibleRect(rect)
            return !rect.contains(event.rawX.toInt(), event.rawY.toInt())
        }
        return false
    }

}
