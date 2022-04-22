package com.base_clean_architecture.custom_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.base_clean_architecture.base.BaseDialogFragment
import com.base_clean_architecture.databinding.CustomDialogFragmentBinding

class ProgressView: BaseDialogFragment<CustomDialogFragmentBinding>() {

    override fun setupView() {
        super.setupView()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): CustomDialogFragmentBinding {
        return  CustomDialogFragmentBinding.inflate(inflater, container, false)
    }
}