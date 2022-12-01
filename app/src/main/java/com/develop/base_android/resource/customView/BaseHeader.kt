package com.develop.base_android.resource.customView

import android.view.View
import com.develop.base_android.resource.utils.setOnSingleClick

interface BaseHeader {
    fun leftView(): View?
    fun rightView(): View?
}

fun BaseHeader.setOnLeftButtonClick(onclick: () -> Unit) {
    leftView()?.setOnSingleClick { onclick.invoke() }
}

fun BaseHeader.setOnRightButtonClick(onclick: () -> Unit) {
    rightView()?.setOnSingleClick { onclick.invoke() }
}
