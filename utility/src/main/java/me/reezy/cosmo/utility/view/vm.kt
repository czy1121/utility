package me.reezy.cosmo.utility.view

import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import me.reezy.cosmo.utility.context.resolveComponentActivity


@MainThread
inline fun <reified VM : ViewModel> View.activityViewModels(): Lazy<VM> {
    val activity = context.resolveComponentActivity()!!
    return ViewModelLazy(VM::class, storeProducer =  {
        activity.viewModelStore
    }, factoryProducer =  {
        activity.defaultViewModelProviderFactory
    }, extrasProducer = {
        activity.defaultViewModelCreationExtras
    })
}