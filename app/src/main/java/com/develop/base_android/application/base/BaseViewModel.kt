package com.develop.base_android.application.base

import androidx.lifecycle.ViewModel
import com.develop.base_android.application.resource.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    val isShowProgress = SingleLiveEvent<Boolean>()
}

fun BaseViewModel.showProgress() {
    if (isShowProgress.value == true) {
        return
    }
    isShowProgress.postValue(true)
}

fun BaseViewModel.hideProgress() {
    if (isShowProgress.value == false) {
        return
    }
    isShowProgress.postValue(false)
}