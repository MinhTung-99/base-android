package com.develop.base_android.application.base.tabbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.develop.base_android.R
import com.develop.base_android.databinding.TabbarItemLayoutBinding

class TabbarItemView : ConstraintLayout {

    private var selectedIcon: Drawable? = null
    private var icon: Drawable? = null

    constructor(context: Context) : super(context) {
        initView(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs, 0)
    }

    constructor(
        context: Context, attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(attrs, defStyleAttr)
    }

    lateinit var binding: TabbarItemLayoutBinding

    private fun initView(attrs: AttributeSet?, @AttrRes defStyleAttr: Int) {
        binding = TabbarItemLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        val customAttributesStyle =
            context.obtainStyledAttributes(attrs, R.styleable.TabbarItemView, defStyleAttr, 0)

        try {
            icon = customAttributesStyle.getDrawable(R.styleable.TabbarItemView_myIcon)
            selectedIcon =
                customAttributesStyle.getDrawable(R.styleable.TabbarItemView_selectedIcon)
            if (selectedIcon == null) selectedIcon = icon
            binding.imageView.setImageDrawable(icon)
        } finally {
            customAttributesStyle.recycle()
        }
        setDot()
    }

    fun setSelectedView(tintColor: Int) {
        binding.imageView.setColorFilter(tintColor)
    }

    fun selectedIC(isSelected: Boolean) {
        binding.imageView.setImageDrawable(if (isSelected) selectedIcon else icon)
    }

    fun selectedBG(isSelected: Boolean) {
        binding.selectBackground.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
    }

    fun setDot(enable: Boolean = false) {
        binding.notifyDot.visibility = if (enable) VISIBLE else INVISIBLE
    }

}