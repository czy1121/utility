package me.reezy.cosmo.utility.delegate


import android.app.Activity
import android.app.Dialog
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment


fun <T : View> Activity.view(@IdRes id: Int): Lazy<T> = lazy { findViewById(id) }
fun <T : View> Fragment.view(@IdRes id: Int): Lazy<T> = lazy { requireView().findViewById(id) }
fun <T : View> Dialog.view(@IdRes id: Int): Lazy<T> = lazy { findViewById(id) }
fun <T : View> View.view(@IdRes id: Int): Lazy<T> = lazy { findViewById(id) }