package com.develop.base_android.application.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.viewbinding.ViewBinding
import com.develop.base_android.R

typealias MyFragment = BaseFragment<*>
typealias MyActivity = BaseActivity<*>

abstract class BaseFragment<B : ViewBinding> : Fragment() {
    lateinit var binding: B

    open fun makeViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
    }

    open fun setupView() {}

    open fun makeViewModel() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        makeViewBinding(inflater, container, savedInstanceState)
        setupView()
        makeViewModel()
        return this.binding.root
    }
}

abstract class BaseVMFragment<V : BaseViewModel, B : ViewBinding> : BaseFragment<B>() {
    abstract val viewModel: V
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.isShowProgress.observe(this) { isShow ->
            (mActivity as? BaseVMActivity<*, *>)?.viewModel?.isShowProgress?.postValue(isShow)
        }
    }
}

val BaseFragment<*>.mActivity: MyActivity?
    get() = this.activity as? MyActivity

fun BaseFragment<*>.pushTo(
    @IdRes resId: Int,
    args: Bundle? = null,
    anim: PUSH_TYPE = PUSH_TYPE.SLIDE
) {
    mActivity?.pushTo(resId, args, anim)
}

fun BaseFragment<*>.popTo(@IdRes destinationId: Int? = null, inclusive: Boolean = false) {
    mActivity?.popTo(destinationId, inclusive)
}

/*fun BaseFragment<*>.popToRoot() {
    mActivity?.popToRoot()
}*/