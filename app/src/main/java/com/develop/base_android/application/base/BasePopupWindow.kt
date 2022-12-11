package com.develop.base_android.application.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.develop.base_android.R
import com.develop.base_android.view.poppup_window.PopWindowEnum
import com.develop.base_android.view.poppup_window.PopWindowListener


abstract class BasePopupWindow {
    abstract val getContext: Context
    abstract val getResId: Int
    abstract val getAction: PopWindowListener

    @SuppressLint("RtlHardcoded")
    fun getPopupWindow() : PopupWindow {
        val inflater = getContext.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = inflater.inflate(getResId, null)
        val popupWindow = PopupWindow(
            v,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        val rlMessage1 = v.findViewById<RelativeLayout>(R.id.rlMessage1)
        val rlMessage2 = v.findViewById<RelativeLayout>(R.id.rlMessage2)
        rlMessage1.setOnClickListener {
            getAction.action(PopWindowEnum.MESSAGE1)
            popupWindow.dismiss()
        }
        rlMessage2.setOnClickListener {
            getAction.action(PopWindowEnum.MESSAGE2)
            popupWindow.dismiss()
        }

        return popupWindow
    }
}

fun PopupWindow.showTopAndRightOfView (view: View, viewContent: View) {
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    showAtLocation(
        view,
        Gravity.NO_GRAVITY,
        location[1],
        location[1] - viewContent.measuredHeight
    )
}

fun PopupWindow.showTopAndLeftOfView (view: View, viewContent: View, with: Int) {
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    showAtLocation(
        view,
        Gravity.NO_GRAVITY,
        location[0] + 50,
        location[1] - viewContent.measuredHeight
    )
}

fun PopupWindow.showBottomAndLeftOfView (view: View) {
    val loc_int = IntArray(2)
    view.getLocationOnScreen(loc_int)
    val location = Rect()
    location.left = loc_int[0]
    location.top = loc_int[1]
    location.right = location.left + view.width
    location.bottom = location.top + view.height

    showAtLocation(
        view,
        Gravity.NO_GRAVITY,
        location.left,
        location.bottom
    )
}

fun PopupWindow.showBottomAndRightOfView (view: View) {
    val loc_int = IntArray(2)
    view.getLocationOnScreen(loc_int)
    val location = Rect()
    location.left = loc_int[0]
    location.top = loc_int[1]
    location.right = location.left + view.width
    location.bottom = location.top + view.height

    showAtLocation(
        view,
        Gravity.NO_GRAVITY,
        location.right,
        location.bottom
    )
}