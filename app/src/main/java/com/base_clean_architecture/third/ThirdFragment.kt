package com.base_clean_architecture.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.base_clean_architecture.base.BaseFragment
import com.base_clean_architecture.base.impl.NavigationImpl
import com.base_clean_architecture.databinding.FragmentThirdBinding

class ThirdFragment: BaseFragment<ThirdViewModel, FragmentThirdBinding>() {

    override fun makeViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        super.setupView()

        binding.btnBackToRoot.setOnClickListener {
            navigation?.pop(NavigationImpl.ROOT_TAG)
        }
    }
}