package com.develop.base_android.resource.customView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.develop.base_android.R
import com.develop.base_android.data.response.utils.getAppCompactColor

class CustomTextView(
    context: Context,
    attrs: AttributeSet
) : AppCompatTextView(context, attrs){

    var font: FontTextView = FontTextView.REGULAR
        set(value) {
            typeface = when (value) {
                FontTextView.REGULAR -> ResourcesCompat.getFont(context, R.font.notosansjp_regular)
                FontTextView.MEDIUM -> ResourcesCompat.getFont(context, R.font.notosansjp_medium)
                FontTextView.BOLD -> ResourcesCompat.getFont(context, R.font.notosansjp_bold)
            }
            field = value
        }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.my_text_view_style)
        when (array.getInt(R.styleable.my_text_view_style_text_color, 1)) {
            1 -> setTextColor(context.getAppCompactColor(R.color.text_title_color))
            2 -> setTextColor(context.getAppCompactColor(R.color.primary_color))
            3 -> setTextColor(context.getAppCompactColor(R.color.warn_color))
            4 -> setTextColor(context.getAppCompactColor(R.color.white))
            5 -> setTextColor(context.getAppCompactColor(R.color.blue_text_color))
        }
        font = FontTextView.getValue(array.getInt(R.styleable.my_text_view_style_font_type, 1))
            ?: FontTextView.REGULAR
        includeFontPadding = false
        array.recycle()
    }

}

enum class FontTextView(val value: Int) {
    REGULAR(value = 1),
    MEDIUM(value = 2),
    BOLD(value = 3);

    companion object {
        fun getValue(int: Int): FontTextView? {
            return values().firstOrNull { it.value == int }
        }
    }
}