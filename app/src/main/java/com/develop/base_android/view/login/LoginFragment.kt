package com.develop.base_android.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.develop.base_android.R
import com.develop.base_android.application.base.BaseVMFragment
import com.develop.base_android.application.base.pushTo
import com.develop.base_android.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseVMFragment<LoginViewModel, FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()

    override fun makeViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        super.makeViewBinding(inflater, container, savedInstanceState)
        binding = FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        super.setupView()

        viewModel.getEntry()

        binding.txtLogin.setOnClickListener {
           // Settings.ACCESS_TOKEN.put("TOKEN")
            pushTo(R.id.action_loginFragment_to_signUpFragment)
        }
    }
}