package com.develop.base_android.application.base.tabbar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.develop.base_android.R
import com.develop.base_android.databinding.TabbarLayoutBinding

class TabbarView(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {

    var binding: TabbarLayoutBinding

    private var selectedColor: Int = context.getColor(R.color.white)
    private var defaultColor: Int = context.getColor(R.color.white)

    var currentSelectedIndex: Int = 0
        set(value) {
            selectItemAt(value)
            didSelectItemAt?.let { it1 -> it1(value) }
            field = value
        }

    var didSelectItemAt: ((Int) -> Unit)? = null

    private var listTabItem = mutableListOf<TabbarItemView>()

    init {

        val customAttributesStyle =
            context.obtainStyledAttributes(attrs, R.styleable.TabbarView, 0, 0)
        try {
            selectedColor = customAttributesStyle.getColor(
                R.styleable.TabbarView_tabSelectedColor,
                Color.WHITE
            )
            defaultColor =
                customAttributesStyle.getColor(R.styleable.TabbarView_defaultColor, Color.WHITE)
        } finally {
            customAttributesStyle.recycle()
        }

        binding = TabbarLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        listTabItem.add(binding.tab1)
        listTabItem.add(binding.tab2)
        /*listTabItem.add(binding.tab3)
        listTabItem.add(binding.tab4)*/
        currentSelectedIndex = 0
        listTabItem.forEachIndexed { index, tabbarItemView ->
            tabbarItemView.setOnClickListener { currentSelectedIndex = index }
        }
    }

    private fun selectItemAt(index: Int) {
        listTabItem.forEachIndexed { index1, view ->
            view.setSelectedView(if (index1 == index) selectedColor else defaultColor)
            view.selectedBG(index1 == index)
            view.selectedIC(index1 == index)
        }
    }

    fun setDot(position: Int, enable: Boolean) {
        listTabItem.getSafe(position)?.setDot(enable)
    }

}

fun <T> List<T>.getSafe(index: Int): T? {
    return if (index < 0 || index >= size) null
    else get(index)
}