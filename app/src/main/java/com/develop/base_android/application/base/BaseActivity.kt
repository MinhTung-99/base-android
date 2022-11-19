package com.develop.base_android.application.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.AnimBuilder
import androidx.navigation.NavController
import androidx.navigation.navOptions
import androidx.viewbinding.ViewBinding
import com.develop.base_android.R
import com.develop.base_android.application.resource.customview.ProgressView

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B

    var navContainer: NavController? = null

    internal var progress: ProgressView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeViewBinding()
        setContentView(binding.root)
        setupView(savedInstanceState)
    }

    open fun makeViewBinding() {}

    open fun setupView(savedInstanceState: Bundle?) {}
}

abstract class BaseVMActivity<VM : BaseViewModel, B : ViewBinding> : BaseActivity<B>() {
    abstract val viewModel: VM

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        viewModel.isShowProgress.observe(this) { isShow ->
            showProgress(isShow)
        }
    }

    private fun showProgress(isShow: Boolean) {
        if (isShow) {
            if (progress == null) {
                progress = ProgressView()
            }
            if (progress?.isVisible == true) {
                return
            }
            progress?.show(supportFragmentManager, "")
        } else {
            progress?.dismiss()
            progress = null
        }
    }
}

enum class PUSH_TYPE(val anim: AnimBuilder) {
    NONE(AnimBuilder().apply {}),
    SLIDE(
        AnimBuilder().apply {
            enter = R.anim.enter_from_right
            exit = R.anim.exit_to_left
            popEnter = R.anim.enter_from_left
            popExit = R.anim.exit_to_right
        }
    ),
    FADE(AnimBuilder().apply {
        enter = R.anim.fade_in
        exit = R.anim.fade_out
    })
}

fun BaseActivity<*>.pushTo(
    @IdRes resId: Int,
    args: Bundle? = null,
    anim: PUSH_TYPE = PUSH_TYPE.SLIDE
) {
    navContainer?.currentDestination?.getAction(resId)?.navOptions?.let {
        navContainer?.navigate(
            resId,
            args,
            navOptions {
                anim {
                    enter = anim.anim.enter
                    exit = anim.anim.exit
                    popEnter = anim.anim.popEnter
                    popExit = anim.anim.popExit
                }
                popUpTo(it.popUpToId) {
                    inclusive = it.isPopUpToInclusive()
                }
            }
        )
    }

}

fun BaseActivity<*>.popTo(@IdRes destinationId: Int? = null, inclusive: Boolean = false) {
    navContainer?.apply {
        if (destinationId == null) popBackStack()
        else popBackStack(destinationId, inclusive)
    }
}

/*fun BaseActivity<*>.popToRoot() {
    rootDes?.let { popTo(it, false) }
}*/

fun BaseActivity<*>.visibleFragment(): Fragment? {
    val navHostFragment: Fragment? =
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    return navHostFragment?.childFragmentManager?.fragments?.get(0)
}