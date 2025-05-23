package me.reezy.cosmo.utility.delegate

import android.app.Dialog
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import me.reezy.cosmo.utility.context.resolveComponentActivity


@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.activityViewModels(): Lazy<VM> = ViewModelLazy(VM::class, storeProducer = {
    viewModelStore
}, factoryProducer = {
    defaultViewModelProviderFactory
}, extrasProducer = {
    defaultViewModelCreationExtras
})

inline fun <reified VM : ViewModel> View.activityViewModels() = context.resolveComponentActivity()!!.activityViewModels<VM>()
inline fun <reified VM : ViewModel> Dialog.activityViewModels() = context.resolveComponentActivity()!!.activityViewModels<VM>()