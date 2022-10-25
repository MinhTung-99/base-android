package com.develop.base_android.application.view.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.develop.base_android.application.base.BaseVMFragment
import com.develop.base_android.databinding.FragmentSignUpBinding

class SignUpFragment : BaseVMFragment<SignUpViewModel, FragmentSignUpBinding>() {
    override val viewModel: SignUpViewModel by viewModels()

    override fun makeViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        super.makeViewBinding(inflater, container, savedInstanceState)
        binding = FragmentSignUpBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        super.setupView()

        binding.txtSignUp.setOnClickListener {
            popTo()
        }
    }
}