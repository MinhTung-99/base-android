package com.base_clean_architecture.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.base_clean_architecture.base.BaseFragment
import com.base_clean_architecture.databinding.FragmentSecondBinding
import com.base_clean_architecture.third.ThirdFragment

class SecondFragment : BaseFragment<SecondViewModel, FragmentSecondBinding>() {

    override fun makeViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        super.setupView()

        binding.btnBack.setOnClickListener {
            navigation?.pop()
        }

        binding.btnNext.setOnClickListener {
            navigation?.push(ThirdFragment())
        }
    }
}