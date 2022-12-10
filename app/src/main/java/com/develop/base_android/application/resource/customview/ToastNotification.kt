package com.develop.base_android.application.resource.customview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.*
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.viewbinding.ViewBinding
import com.develop.base_android.application.base.CoroutineLauncher
import kotlinx.coroutines.delay
import kotlin.math.abs

class ToastNotification<B : ViewBinding>(activity: Activity) {
    private val scope: CoroutineLauncher by lazy {
        return@lazy CoroutineLauncher()
    }
    private val mRootView: FrameLayout
    private var mView: View? = null
    var binding: B? = null
    private var mListener: View.OnClickListener? = null
    private var mDuration: Long = 3000
    private var animDuration: Long = 500
    private var mInterpolator: Interpolator = AccelerateDecelerateInterpolator()
    private var mValueAnimator: ValueAnimator? = null
    private var mHeight = 0
    private var offset = 0
    private val touchSlop: Int
    private var startX = 0f
    private var startY = 0f
    private var lastY = 0f
    private var maxY = 0f
    private var canClick = false

    fun setContentView(view: View): ToastNotification<B> {
        mView = view
        setTouchEvent()
        return this
    }

    fun setContentView(makeBinding: (FrameLayout) -> B): ToastNotification<B> {
        binding = makeBinding(mRootView)
        return this.setContentView(
            binding!!.root
        )
    }

    fun setOnClickListener(l: View.OnClickListener?): ToastNotification<B> {
        mListener = l
        return this
    }

    fun setDuration(@IntRange(from = 2000, to = 10000) duration: Long): ToastNotification<B> {
        mDuration = duration
        return this
    }

    fun setAnimDuration(@IntRange(from = 100, to = 1000) duration: Long): ToastNotification<B> {
        animDuration = duration
        return this
    }

    fun setInterpolator(interpolator: Interpolator): ToastNotification<B> {
        mInterpolator = interpolator
        return this
    }

    private fun measureHeight(): Int {
        var lp = mView?.layoutParams
        if (lp == null) {
            lp = MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        if (lp is MarginLayoutParams) {
            lp.topMargin += offset
        }
        val widthSpec: Int = if (lp.width > 0) {
            View.MeasureSpec.makeMeasureSpec(lp.width, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST)
        }
        val heightSpec: Int = if (lp.height > 0) {
            View.MeasureSpec.makeMeasureSpec(lp.height, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }
        mView?.measure(widthSpec, heightSpec)
        return mView?.measuredHeight ?: 0
    }

    fun show() {
        mView ?: return
        scope.launch {
            if (mHeight == 0) {
                mHeight = measureHeight()
            }
            if (mView?.parent == null) {
                mRootView.addView(mView)
                mView?.translationY = maxTranslationY
            }
            enterAnim(mView!!.translationY, 0f)
            delay(mDuration)
            hide()
        }
    }

    private fun hide() {
        mView ?: return
        if (mValueAnimator != null) {
            mValueAnimator?.cancel()
            mValueAnimator = null
        }
        exitAnim(mView!!.translationY, maxTranslationY)
    }

    private val maxTranslationY: Float
        get() = (-(mHeight + offset)).toFloat()

    private fun enterAnim(start: Float, end: Float) {
        mValueAnimator = ValueAnimator.ofFloat(start, end)
        mValueAnimator?.interpolator = mInterpolator
        mValueAnimator?.duration = animDuration
        mValueAnimator?.addUpdateListener(AnimatorUpdateListener { animation ->
            if (mView == null) {
                mValueAnimator?.cancel()
                mValueAnimator = null
                return@AnimatorUpdateListener
            }
            val value = animation.animatedValue as Float
            mView?.translationY = value
            maxY = mView!!.y
        })
        mValueAnimator?.start()
    }

    private fun exitAnim(start: Float, end: Float) {
        mValueAnimator = ValueAnimator.ofFloat(start, end)
        mValueAnimator?.interpolator = mInterpolator
        mValueAnimator?.duration = animDuration
        mValueAnimator?.addUpdateListener(AnimatorUpdateListener { animation ->
            if (mView == null) {
                mValueAnimator?.cancel()
                mValueAnimator = null
                return@AnimatorUpdateListener
            }
            val value = animation.animatedValue as Float
            mView?.translationY = value
        })
        mValueAnimator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (mView != null) {
                    mRootView.removeView(mView)
                }
            }
        })
        mValueAnimator?.start()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchEvent() {
        mView?.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    canClick = true
                    startX = event.rawX
                    startY = event.rawY
                    lastY = startY
                    scope.cancelCoroutines()
                }
                MotionEvent.ACTION_MOVE -> {
                    val x = event.rawX
                    val y = event.rawY
                    updateY(y)
                    lastY = y
                    if (!isClick(x, y)) {
                        canClick = false
                    }
                }
                MotionEvent.ACTION_UP -> if (mListener != null && canClick) {
                    mListener!!.onClick(v)
                    hide()
                } else {
                    if (maxY - v.y > offset) {
                        hide()
                    } else {
                        show()
                    }
                }
            }
            true
        }
    }

    private fun updateY(y: Float) {
        val newY = mView!!.y + (y - lastY)
        if (newY < maxY) {
            mView!!.y = newY
        }
    }

    private fun isClick(endX: Float, endY: Float): Boolean {
        return abs(endX - startX) < touchSlop && abs(endY - startY) < touchSlop
    }

    /**
     * Gets the height of the status bar
     */
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    private fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    init {
        mRootView = activity.window.decorView as FrameLayout
        offset = getStatusBarHeight(activity)
        touchSlop = ViewConfiguration.get(mRootView.context).scaledTouchSlop
    }
}
