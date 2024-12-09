package me.reezy.cosmo.utility.context

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy


@MainThread
inline fun <reified VM : ViewModel> Context.activityViewModels(): Lazy<VM> {
    val activity = resolveComponentActivity()!!
    return ViewModelLazy(VM::class, storeProducer =  {
        activity.viewModelStore
    }, factoryProducer =  {
        activity.defaultViewModelProviderFactory
    }, extrasProducer = {
        activity.defaultViewModelCreationExtras
    })
}

inline fun <reified VM : ViewModel> View.activityViewModels() = context.activityViewModels<VM>()

inline fun <reified VM : ViewModel> Dialog.activityViewModels() = context.activityViewModels<VM>()