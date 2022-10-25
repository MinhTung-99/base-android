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

    fun pushTo(@IdRes resId: Int, args: Bundle? = null) {
        binding.root.findNavController().currentDestination?.getAction(resId)?.navOptions?.let {
            binding.root.findNavController().navigate(
                resId,
                args,
                navOptions { // Use the Kotlin DSL for building NavOptions
                    anim {
                        enter = R.anim.enter_from_right
                        exit = R.anim.exit_to_left
                        popEnter = R.anim.enter_from_left
                        popExit = R.anim.exit_to_right
                    }
                    popUpTo(it.popUpToId) {
                        inclusive = it.isPopUpToInclusive()
                    }
                }
            )
        }
    }

    fun pushFadeTo(@IdRes resId: Int, args: Bundle? = null) {
        binding.root.findNavController().currentDestination?.getAction(resId)?.navOptions?.let {
            binding.root.findNavController().navigate(
                resId,
                args,
                navOptions { // Use the Kotlin DSL for building NavOptions
                    anim {
                        enter = R.anim.fade_in
                        exit = R.anim.fade_out
                    }
                    popUpTo(it.popUpToId) {
                        inclusive = it.isPopUpToInclusive()
                    }
                }
            )
        }
    }

    fun popTo(@IdRes destinationId: Int? = null, inclusive: Boolean = false) {
        binding.root.findNavController().apply {
            if (destinationId == null) popBackStack()
            else popBackStack(destinationId, inclusive)
        }
    }

    /*fun popToRoot() {
        mActivity?.rootId?.let { popTo(it, false) }
    }*/
}

abstract class BaseVMFragment<V : BaseViewModel, B : ViewBinding> : BaseFragment<B>() {
    abstract val viewModel: V
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}