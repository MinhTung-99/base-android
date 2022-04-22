package com.base_clean_architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<B: ViewBinding>: DialogFragment() {
    lateinit var binding: B

    abstract fun createViewBinding(inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   savedInstanceState: Bundle?) : B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = createViewBinding(inflater, container, savedInstanceState)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    open  fun setupView() {}
}