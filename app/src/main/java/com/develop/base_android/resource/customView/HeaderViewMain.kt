package com.develop.base_android.resource.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.develop.base_android.databinding.ViewHeaderMainBinding

class HeaderViewMain : FrameLayout, BaseHeader {
    constructor(context: Context) : super(context) {
        initView(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs, 0)
    }

    constructor(
        context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(attrs, defStyleAttr)
    }

    lateinit var binding: ViewHeaderMainBinding

    private fun initView(attrs: AttributeSet?, @AttrRes defStyleAttr: Int) {
        binding = ViewHeaderMainBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun leftView(): View = binding.leftImage

    override fun rightView(): View = binding.rightImage

}