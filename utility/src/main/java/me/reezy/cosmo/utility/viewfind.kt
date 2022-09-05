package me.reezy.cosmo.utility


import android.app.Activity
import android.app.Dialog
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView


fun <T : View> Activity.view(id: Int): Lazy<T> = lazy { findViewById(id) }
fun <T : View> Fragment.view(id: Int): Lazy<T> = lazy { requireView().findViewById(id) }
fun <T : View> Dialog.view(id: Int): Lazy<T> = lazy { findViewById(id) }
fun <T : View> View.view(id: Int): Lazy<T> = lazy { findViewById(id) }
fun <T : View> RecyclerView.ViewHolder.view(id: Int): Lazy<T> = lazy { itemView.findViewById(id) }