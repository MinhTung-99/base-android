package com.develop.base_android.view.poppup_window

import android.content.Context
import android.view.View
import com.develop.base_android.R
import com.develop.base_android.application.base.BasePopupWindow

class PopWindow(
    private val context: Context,
    private val view: View,
    private val action: PopWindowListener
) : BasePopupWindow() {

    override val getContext: Context
        get() = context

    override val getResId: Int
        get() = R.layout.poup_windown

    override val getAction: PopWindowListener
        get() = action
}

enum class PopWindowEnum {
    MESSAGE1, MESSAGE2
}

interface PopWindowListener {
    fun action(popWindowEnum: PopWindowEnum)
}